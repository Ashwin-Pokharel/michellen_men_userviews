package users.userviews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class userController {

    @Autowired
    private EurekaClient discoveryClient;

    public static final String restURL = "";


    @GetMapping("/")
    public String landing(Model model) {
        model.addAttribute("message", "Welcome to the Michellen Men");
        return "landing";
    }

    @PostMapping("/logout")
    public ModelAndView logout(@CookieValue("user_token")String token, @CookieValue("user_id") String user_id,Model model, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("landing");
        model.addAttribute("message", "You have been logged out");
        Cookie cookie = new Cookie("user_token", "null");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        cookie = new Cookie("user_id", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return mv;

    }
    
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("message", "Welcome to Michellen Men");
        model.addAttribute("loginForm", new loginForm());
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("login") loginForm login, Model model, HttpServletResponse response){
        model.addAttribute("loginForm", new loginForm());
        model.addAttribute("message", "Login Failed");
        RestTemplate restTemplate = new RestTemplate();

        String username = login.username;
        String password = login.password;
        System.out.println(username);
        System.out.println(password);
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("michellen_men_userservice", false);
        String url = instance.getHomePageUrl();
        url += "user/authenticateUser";

        Map<String , String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
       
        try{
            loginResponse loginServiceResponse = restTemplate.postForObject(url, map, loginResponse.class);
            if(loginServiceResponse.getStatus().equals("success")){
                String token = loginServiceResponse.getToken();
                String userId = loginServiceResponse.getUserId();
                Cookie cookie = new Cookie("user_token", token);
                Cookie idcookie = new Cookie("user_id", userId);
                response.addCookie(cookie);
                response.addCookie(idcookie);
                model.addAttribute("user_id", userId);
                model.addAttribute("jwt_token", token);
                model.addAttribute("message", "Login Successful");
                return new ModelAndView("redirect:/userDetails");
            }
            else{
                System.out.println(response);
                System.out.println("Login Failed");
                return new ModelAndView("login");
            }
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Login Failed");
            return new ModelAndView("login");
        }
    }
    

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("message", "Welcome to Michellen Men");
        model.addAttribute("RegisterForm" , new RegisterForm());
        return "register";
    }

    @PostMapping("/addNewUser")
    public ModelAndView register(@ModelAttribute("login") RegisterForm registration,Model model){
        model.addAttribute("message", "login failed");

        RestTemplate restTemplate = new RestTemplate();
        String username = registration.username;
        String password = registration.password;
        String email = registration.email;
        String name = registration.name;
        String phoneNumer = registration.phoneNumber;
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("michellen_men_userservice", false);
        String url = instance.getHomePageUrl();
        url += "user/addNewUser";

        try{
            Map<String , String> map = new HashMap<>();
            map.put("username", username);
            map.put("password", password);
            map.put("email", email);
            map.put("name", name);
            map.put("phoneNumber", phoneNumer);

            ResponseEntity<String> response= restTemplate.postForEntity(url , map, String.class);
            System.out.println(response);
            if(response.getStatusCode() == HttpStatus.OK){
                model.addAttribute("message", "Registration Successful");
                model.addAttribute("loginForm", new loginForm());
                return new ModelAndView("login");
            }
            else{
                model.addAttribute("message", "Registration Failed");
                model.addAttribute("RegisterForm", new RegisterForm());
                return new ModelAndView("register");
            }
        }catch(Exception e){
            System.out.println(e);
            model.addAttribute("message", "Registration Failed");
            model.addAttribute("RegisterForm", new RegisterForm());
            return new ModelAndView("register");
        }

    }

    @GetMapping("/userDetails")
    public ModelAndView userDetails(@CookieValue("user_token")String token, @CookieValue("user_id") String user_id ,Model model){
        model.addAttribute("message", "login failed");
        model.addAttribute("updateBioForm", new updateBioForm());
        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("michellen_men_userservice", false);
        String url = instance.getHomePageUrl();
        url += "user/getUserDetails";
        try{
            Map<String , String> map = new HashMap<>();
            map.put("token" , token);
            UserResponse response = restTemplate.postForObject(url, map, UserResponse.class);
            System.out.println(response.toString());
            System.out.println(response.getUsername());
            System.out.println(response.getName());
            model.addAttribute("username", response.getUsername());
            model.addAttribute("email", response.getEmail());
            model.addAttribute("name", response.getName());
            model.addAttribute("phoneNumber", response.getPhoneNumber());
            model.addAttribute("bio", response.getBio());
            return new ModelAndView("userDetails");

        }catch(Exception e){
                model.addAttribute("message", "Registration Successful");
                model.addAttribute("loginForm", new loginForm());
                return new ModelAndView("login");
        }
    
    }

    @PostMapping("/modifyUser")
    public ModelAndView modifyUser(@CookieValue("user_token")String token, @CookieValue("user_id") String user_id, updateBioForm updateBioForm ,Model model){
        model.addAttribute("message", "login failed");
        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("michellen_men_userservice" , false);
        String url = instance.getHomePageUrl();
        url += "user/updateUserBio";
        try{
            Map<String , String> map = new HashMap<>();
            map.put("token", token);
            map.put("bio" , updateBioForm.bio);
            UserResponse response = restTemplate.postForObject(url, map, UserResponse.class);
            System.out.println(response.toString());
            System.out.println(response.getUsername());
            System.out.println(response.getName());
            model.addAttribute("username", response.getUsername());
            model.addAttribute("email", response.getEmail());
            model.addAttribute("name", response.getName());
            model.addAttribute("phoneNumber", response.getPhoneNumber());
            model.addAttribute("bio", response.getBio());
            model.addAttribute("updateBioForm", new updateBioForm());
            return new ModelAndView("userDetails");

        }catch(Exception e){
            model.addAttribute("message", "Registration Successful");
            model.addAttribute("loginForm", new loginForm());
            return new ModelAndView("login");

        }
    }

    @GetMapping("/userReviews")
    public ModelAndView userReviews(@CookieValue("user_token")String token, @CookieValue("user_id") String user_id ,Model model){
        System.out.println("request revieved");
        model.addAttribute("message", "login failed");
        RestTemplate restTemplate = new RestTemplate();
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("michellen_men_userservice" , false);
        String url = instance.getHomePageUrl();
        try{
            ArrayList<userReviews> user_reviews = new ArrayList<>();
            userReviews review1 = new userReviews(user_id, "mcdonalds", 2, "food very bland");
            userReviews review2 = new userReviews(user_id, "tacobell", 5, "food was amazing and tasty");
            userReviews review3 = new userReviews(user_id, "wendys", 2, "food very dry");
            userReviews review4 = new userReviews(user_id, "popeyes", 5, "food very bland");
            user_reviews.add(review1);
            user_reviews.add(review2);
            user_reviews.add(review3);
            user_reviews.add(review4);
            model.addAttribute("recieved_reviews", user_reviews);
            return new ModelAndView("userReviews");

        }
        catch (Exception e){
            System.out.println(e);
            return new ModelAndView("userDetails");

        }
    }

    

    
}

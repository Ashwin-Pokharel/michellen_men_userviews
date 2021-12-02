package users.userviews;

public class loginResponse {
    public String status;
    public String message;
    public String token;
    public String userId;

    public loginResponse(){
        this.status = "";
        this.message = "";
        this.token = "";
        this.userId = "";

    }

    public loginResponse(String status, String message, String token , String userId) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "loginResponse [status=" + status + ", message=" + message + ", token=" + token + "]";
    }

    
}

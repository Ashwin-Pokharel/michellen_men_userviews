package users.userviews;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories
@EnableDiscoveryClient
public class UserviewsApplication implements CommandLineRunner {
	
	

	public static void main(String[] args) {
		SpringApplication.run(UserviewsApplication.class, args);
	}


	@Override
    public void run(String... args) throws Exception {
        //anything that needs to run at the start of the application
    }
}

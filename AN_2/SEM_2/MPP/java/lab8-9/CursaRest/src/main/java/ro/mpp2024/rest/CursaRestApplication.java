package ro.mpp2024.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
@ComponentScan(basePackages = "ro.mpp2024")
@EntityScan(basePackages = "ro.mpp2024.model")
public class CursaRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CursaRestApplication.class, args);
    }
}

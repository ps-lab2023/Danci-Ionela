package com.example.demo;
import com.example.demo.model.User;
import com.example.demo.repository.*;
import com.example.demo.service.impl.UserServiceImplementare;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import static com.example.demo.enums.UserType.ADMIN;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository ) {
        return args -> {
            UserServiceImplementare userServiceImplementare=new UserServiceImplementare(userRepository);
            User admin=new User("admin@gmail.com","adminn",ADMIN);
            try{
                userServiceImplementare.createUser(admin);
            }catch (Exception exception)
            {
                System.out.println("\"admin@gmail.com\" already exists");
            }

        };
    }

}

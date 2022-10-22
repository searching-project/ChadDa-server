package com.search.instagramsearching;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class InstagramSearchingApplication {
    public static void main(String[] args) {
        SpringApplication.run(InstagramSearchingApplication.class, args);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void init(){
//        String password;
//        password = "ab1234";
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode(password);
//        System.out.println(hashedPassword);
//        System.out.println(passwordEncoder.matches("ab1234", "$2a$10$bFx9K6pI5HVze/OqbVo0vOm2VAimgZ1jAEwUSkSCkd1ZZhIlmwADm"));
//
//
//    }
}

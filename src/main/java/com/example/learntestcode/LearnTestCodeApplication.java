package com.example.learntestcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/env.properties")
@SpringBootApplication
public class LearnTestCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnTestCodeApplication.class, args);
    }

}

package com.example.demo.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.demo.feign")
@EnableFeignClients
public class MyApp {

    public static void main(String[] args) {
        SpringApplication.run(MyApp.class);
    }

    @Bean
    CommandLineRunner commandLineRunner(FeignExample feignExample){
        return args -> {
            Post postById = feignExample.getPostById(1L);
            System.out.println(postById);
        };
    }
}

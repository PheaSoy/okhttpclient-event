package com.example.demo;

import com.example.demo.config.BookConfig;
import com.example.demo.okhttp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@SpringBootApplication
@RestController
@EnableCaching
public class DemoApplication {


    public static void main(String[] args) throws IOException {

        SpringApplication.run(DemoApplication.class, args);

    }

    @Autowired
    private BookConfig bookConfig;

    @GetMapping("/backend/greeting")
    public String greet() {
        return " book config" + bookConfig.toString();
    }
}

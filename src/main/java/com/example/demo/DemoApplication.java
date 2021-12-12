package com.example.demo;

import com.example.demo.config.BookConfig;
import com.example.demo.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@SpringBootApplication
@RestController
@EnableCaching
@EnableScheduling
@Slf4j
@ImportResource("classpath:beans.xml")
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

    @PostMapping("/backend/greeting")
    public String createGreeting() {
        return " book config" + bookConfig.toString();

    }
    @Autowired
    MyService myService;
//
//    @Scheduled(fixedDelay = 100)
//    public void runMySchedule(){
//        myService.doSomething(1);
//
//    }
//    @Scheduled(fixedDelay = 100)
//    public void runMySchedule2(){
//        myService.doSomething(2);
//
//    }
//    @Scheduled(fixedDelay = 100)
//    public void runMySchedule3(){
//        myService.doSomething(3);
//
//    }
//    @Scheduled(fixedDelay = 100)
//    public void runMySchedule4(){
//        myService.doSomething(4);
//
//    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        log.info(
                "Fixed rate task async - :{}" , System.currentTimeMillis() / 1000);
        Thread.sleep(2000);
    }

}

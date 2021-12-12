package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class MyService {

    void doSomething(int i){
        try{
            Thread.sleep(200);
            log.info("Inside my service i: {} :{}",i, UUID.randomUUID().toString());

        }catch (Exception exception){

        }
    }
}

package com.example.demo;

import com.example.demo.okhttp.SPIClientBaseResponse;
import com.example.demo.okhttp.SPIClientProperties;
import com.example.demo.okhttp.SPIFactory;
import com.example.demo.okhttp.SPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppInit {

    @Autowired
    SPIService spiService;

    @Bean(name = "command2")
    CommandLineRunner commandLineRunner(){
        return args -> {

            SPIClientBaseResponse execute = SPIFactory
                    .create("spi-name")
                    .retry()
                    .restClient()
                    .execute();

            SPIClientBaseResponse execute2 = SPIFactory
                    .create("spi-name2", "service_id", (s) -> spiService.getSPIProperties(s))
                    .restClient()
                    .execute();

            SPIClientBaseResponse execute3 = SPIFactory
                    .create("spi-name2", "service_id", (s) -> spiService.getSPIProperties(s))
                    .restClient()
                    .execute();

            System.out.println("execute code=>" + execute.isSuccessful());
            System.out.println("execute2 code=>" + execute2.isSuccessful());
            System.out.println("execute3 code=>" + execute3.isSuccessful());
    };
    }
}

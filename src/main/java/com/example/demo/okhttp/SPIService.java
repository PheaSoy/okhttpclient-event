package com.example.demo.okhttp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SPIService {

    @Cacheable(value = "spi-cache",key = "#serviceId")
    public SPIClientProperties getSPIProperties(String serviceId) {
        log.info("Retrieving spi prop with service id:{}",serviceId);
        return new SPIClientProperties();
    }
}

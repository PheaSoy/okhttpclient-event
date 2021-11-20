package com.example.demo.okhttp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.config.client.RetryProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@Slf4j
public class SPIFactory {

    private SPIClientProperties spiClientProperties = new SPIClientProperties();

    private RetryProp retryProp;

    public SPIFactory() {
    }

    private SPIFactory(SPIClientProperties spiClientProperties) {
        this.spiClientProperties = spiClientProperties;
    }

    public static SPIFactory create(String name, String serviceId,Function<String, SPIClientProperties> spiFun) {
        log.info("Create factory http client with name:{}, service id:{}",name,serviceId);
        SPIClientProperties spiClientPropertiesApply = spiFun.apply(serviceId);
        return new SPIFactory(spiClientPropertiesApply);
    }

    public static SPIFactory create(String name) {
        return new SPIFactory();
    }

    public SPIFactory retry() {
        RetryProp retryProp = RetryProp.builder()
                .retryAble(true)
                .build();
        SPIFactory spiFactory = new SPIFactory();
        spiFactory.setRetryProp(retryProp);
        return spiFactory;
    }

    public SPIFactory retry(RetryProp retryProp) {
        SPIFactory spiFactory = new SPIFactory();
        spiFactory.setRetryProp(retryProp);
        return spiFactory;
    }

    public SPIClient restClient() {
        return new OkHttpSPIClient(this.getSpiClientProperties());
    }

}

package com.example.demo.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpClientConfig {

    private final Long READ_TIMEOUT = 30L;
    private final Long WRITE_TIMEOUT = 30L;

    @Bean(name = "globalOkHttpClient")
    OkHttpClient okHttpClient() {
        OkHttpClient globalOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return globalOkHttpClient;
    }

}

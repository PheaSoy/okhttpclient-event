package com.example.demo.okhttp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class SPIClientProperties {

    private MessageType messageType = MessageType.REST;

    private boolean isAsyncRequest = false;

    private int maxRequestPerRoute = 10;

    private String providerUrl = URLConstants.HOST;

    private int readTimeout = 30;

    private int writeTimeout = 30;

    private String topic = "outbound";

    public SPIClientProperties() {
    }
}

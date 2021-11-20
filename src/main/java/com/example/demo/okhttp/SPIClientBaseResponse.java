package com.example.demo.okhttp;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SPIClientBaseResponse<T> {
    boolean isSuccessful;
    T data;

}

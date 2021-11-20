package com.example.demo.okhttp;

import lombok.Getter;

import java.io.IOException;

public interface SPIClient<T extends SPIClientBaseResponse> {
    T execute() throws IOException;
}

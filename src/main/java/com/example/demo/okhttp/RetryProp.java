package com.example.demo.okhttp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetryProp {

    private boolean retryAble = false;

    private int retryAttempts = 3;

    private int retryDelayedInSeconds = 1;
}

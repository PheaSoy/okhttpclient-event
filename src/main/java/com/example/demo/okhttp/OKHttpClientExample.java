package com.example.demo.okhttp;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OKHttpClientExample {

    public void testCall1() throws IOException {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(10);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(1000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .dispatcher(dispatcher)
                .build();
//        HttpUrl url = new HttpUrl.Builder()
//                .host(URLConstants.HOST).build();

        Request request = new Request.Builder()
                .url(URLConstants.HOST)
                .get()
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        System.out.println("Call 1 status code:" + response.code());
        Call call2 = client.newCall(request);
        call2.timeout().timeout(1, TimeUnit.SECONDS); // Set timeout

        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failed:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("Call 2 success status code:" + response.code());
            }
        });


        System.out.println("Max connection per host settings:" + client.dispatcher().getMaxRequestsPerHost());
    }

    public static void main(String[] args) throws IOException {
        new OKHttpClientExample().testCall1();
    }

}

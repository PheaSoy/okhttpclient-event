package com.example.demo;


import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.LoggingEventListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class OkHttpTest {

    private MockWebServer mockWebServer;

    int port = 9098;
    OkHttpClient globalClient = new OkHttpClient.Builder()
            .readTimeout(1000, TimeUnit.MILLISECONDS)
            .writeTimeout(1000, TimeUnit.MILLISECONDS)
//            .connectionPool();// Max Id Default = Max Idle = 5, time 5 minutes
            .build();
    HttpUrl.Builder httpBuilder = new HttpUrl.Builder();


    @BeforeEach
    void setup() throws IOException {
        globalClient.dispatcher().setMaxRequestsPerHost(20);
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start(port);
        // Set HttpUrl Builder
        httpBuilder.host(this.mockWebServer.getHostName())
                .port(this.mockWebServer.getPort())
                .scheme("http");
    }

    void prepareResponse(Consumer<MockResponse> consumer) {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        this.mockWebServer.enqueue(response);
    }
    void prepareResponse(Consumer<MockResponse> consumer,int ms) throws InterruptedException {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        consumer.wait(ms);
        this.mockWebServer.enqueue(response);
    }


    @Test
    void test_find_book() throws IOException {
        prepareResponse(con -> con.setResponseCode(200));
        OkHttpClient bookClient = globalClient.newBuilder().build();
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .get()
                .build();
        Call call = bookClient.newCall(request);
        Response response = call.execute();
        Assertions.assertThat(response.code()).isEqualTo(200);
    }

    @Test
    void test_returnError() throws IOException {

        prepareResponse(con -> con.setResponseCode(400));
        OkHttpClient bookClient = globalClient.newBuilder().build();
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .get()
                .build();
        Call call = bookClient.newCall(request);
        Response response = call.execute();
        Assertions.assertThat(response.code()).isEqualTo(400);
    }

    @Test
    void test_returnError_AddInterceptor() throws IOException {

        prepareResponse(con -> con.setResponseCode(200));
        OkHttpClient bookClient = globalClient.newBuilder()
                .addInterceptor(new MyHttpLoggingInterceptor())
                .build();

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .get()
                .build();
        Call call = bookClient.newCall(request);
        Response response = call.execute();
        Assertions.assertThat(response.code()).isEqualTo(200);
    }

    @Test
    void testEvent_Capture() throws IOException, InterruptedException {
        prepareResponse(con -> con.setResponseCode(200));
        OkHttpClient bookClient = globalClient.newBuilder()
                .addInterceptor(new MyHttpLoggingInterceptor())
                .eventListener(new PrintingEventListener()) // Event capture
                .build();

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .get()
                .build();
        Call call = bookClient.newCall(request);
        Response response = call.execute();
        Assertions.assertThat(response.code()).isEqualTo(200);

    }

    @Test
    void test_whenMaxConnectionPerHostReached_ShouldWait() throws IOException {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient bookClient = globalClient.newBuilder()
                .addInterceptor(new MyHttpLoggingInterceptor())
                .addInterceptor(logging)
                .eventListener(new PrintingEventListener())
                .build();
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .get()
                .build();

        bookClient.dispatcher().setMaxRequestsPerHost(1);
        bookClient.dispatcher().setMaxRequests(10);
        Call call = bookClient.newCall(request);
        Call call2 = bookClient.newCall(request);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("I am done.");
            }
        });
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("I am done.");
            }
        });
    }


    class MyHttpLoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            System.out.println("Request URL:" + request.url() + "=> Method:" + request.method());
            Response response = chain.proceed(request);
            if (response.isSuccessful())
                System.out.println("Response is success full from remote call.");
            else System.out.println("Error response from remote call with status code" + response.code());
            return response;
        }

    }
}





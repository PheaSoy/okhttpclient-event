package com.example.demo.okhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.Request.Builder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestClientRaceCondition{

    OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();

    String url = "xxxxxxxx";

    private final ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> headermap = Map.of("addInfo3", "12223", "cif", "001000017",
            "client_id", "xxx", "client_secret", "xxxxxxxxxx",
            "x-api-key", "xxxxx");

    void send(UserRequest userRequest,String orderId)  {
        log.info("Sending the request");
        try {
            String requestBodyStr = objectMapper.writeValueAsString(userRequest);
            Headers headers = Headers.of(headermap);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestBodyStr);
            Request request = new Request.Builder()
                    .url(url+orderId)
                    .post(requestBody)
                    .headers(headers)
                    .build();

            Call call = client.newCall(request);
            Response execute = call.execute();
            System.out.println(execute.code());
            System.out.println(execute.body().string());
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {

        TestClientRaceCondition testClientRaceCondition = new TestClientRaceCondition();
        UserRequest userRequest = new UserRequest();
        userRequest.setCif("154565646");

        Runnable runnable1 = () -> testClientRaceCondition.send(userRequest,"d3d37922-e23a-4cf8-a79f-c132d9841089");

        Runnable runnable2 = () -> testClientRaceCondition.send(userRequest,"94e9000a-ec4f-4312-89a4-f830b4b5df1f");

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.execute(runnable1);

        executorService.execute(runnable2);

        // 3000
        // T1= 2000, T2 = 2000

    }
}

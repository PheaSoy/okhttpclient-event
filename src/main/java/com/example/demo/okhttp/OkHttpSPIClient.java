package com.example.demo.okhttp;

import lombok.Getter;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Getter
public class OkHttpSPIClient implements SPIClient {

    private SPIClientProperties spiClientProperties = new SPIClientProperties();

    OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(this.spiClientProperties.getReadTimeout(), TimeUnit.SECONDS)
            .writeTimeout(this.spiClientProperties.getWriteTimeout(), TimeUnit.SECONDS)
            .build();

    public OkHttpSPIClient(SPIClientProperties spiClientProperties) {
        this.spiClientProperties = spiClientProperties;
    }

    public Response call() throws IOException {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(this.spiClientProperties.getMaxRequestPerRoute());
        var newClient = client.newBuilder().dispatcher(dispatcher);
        Request request = new Request.Builder()
                .url(this.spiClientProperties.getProviderUrl())
                .get()
                .build();
        Call call = client.newCall(request);
        if (this.spiClientProperties.isAsyncRequest()) {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                }
            });
        } else {
            return call.execute();
        }
        return null;
    }

    @Override
    public SPIClientBaseResponse execute() throws IOException {
        Response response = this.call();
        return SPIClientBaseResponse.builder()
                .isSuccessful(response.isSuccessful())
                .build();
    }
}

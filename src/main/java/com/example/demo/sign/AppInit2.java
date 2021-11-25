package com.example.demo.sign;

import com.example.demo.okhttp.SPIClientBaseResponse;
import com.example.demo.okhttp.SPIFactory;
import com.example.demo.okhttp.SPIService;
import com.example.demo.okhttp.URLConstants;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppInit2 {
    //private FeignClientBuilder feignClientBuilder;
    @Value("${some.key:false}")
    private boolean booleanWithDefaultValue;

    // FeignClientBuilder.Builder builder = this.feignClientBuilder.forType(PostFeignClient.class, "post_client");

    @Bean(name = "command3")
    CommandLineRunner commandLineRunner() {
        return args -> {
         //   builder.url(URLConstants.HOST).build();
            System.out.println(booleanWithDefaultValue);
        };
    }

    @FeignClient("post_client")
    interface PostFeignClient {

    }
}

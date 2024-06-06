package com.example.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diaoyn
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.example.consumer", "com.example.common"})
public class DemoServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoServiceConsumerApplication.class, args);
    }

}

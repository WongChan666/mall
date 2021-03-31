package com.wong.shopingmall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Wong
 * @Description: 认证中心主启动类
 * @DateTime: 2021/3/18 6:14 下午
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class WongAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(WongAuthApplication.class);
    }
}

package com.pokaboo.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author pokaboo
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.pokaboo"})
@MapperScan("com.pokaboo.staservice.mapper")
@EnableDiscoveryClient
@EnableFeignClients
/**
 * 开启定时任务
 */
@EnableScheduling
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}

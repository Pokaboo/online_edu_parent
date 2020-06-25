package com.pokaboo.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Pokaboo
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.pokaboo"})
/**
 * nacos注册
 */
@EnableDiscoveryClient
/**
 * 服务调用Feign
 */
@EnableFeignClients
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}

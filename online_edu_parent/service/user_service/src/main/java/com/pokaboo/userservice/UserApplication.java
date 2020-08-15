package com.pokaboo.userservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author pokaboo
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.pokaboo"})
@MapperScan("com.pokaboo.userservice.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}

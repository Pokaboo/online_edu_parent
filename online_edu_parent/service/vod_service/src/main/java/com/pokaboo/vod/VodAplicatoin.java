package com.pokaboo.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author pokaboo
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.pokaboo"})
@EnableDiscoveryClient
public class VodAplicatoin {

    public static void main(String[] args) {
        SpringApplication.run(VodAplicatoin.class, args);
    }
}

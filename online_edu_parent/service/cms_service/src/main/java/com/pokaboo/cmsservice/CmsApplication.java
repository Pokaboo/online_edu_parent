package com.pokaboo.cmsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author pokaboo
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.pokaboo"})
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}


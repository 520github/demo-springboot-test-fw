package org.sunso.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication() // scanBasePackages = {"org.sunso.demo","org.sunso.parallel"}
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
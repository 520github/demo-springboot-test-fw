package org.sunso.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.sunso.guard.ratelimiter.annotation.EnableGuardRateLimiter;

@SpringBootApplication()
@EnableGuardRateLimiter(mode = AdviceMode.ASPECTJ)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

//@EnableAspectJAutoProxy
//@EnableTransactionManagement
// scanBasePackages = {"org.sunso.demo","org.sunso.parallel"}
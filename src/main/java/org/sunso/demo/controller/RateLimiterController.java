package org.sunso.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.sunso.guard.ratelimiter.annotation.RateLimiter;
import org.sunso.guard.ratelimiter.annotation.RateLimiterType;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RateLimiterController {

    @ResponseBody
    @RequestMapping("/rate/yes")
    @RateLimiter(groupKey = "rate:limit:test:1" ,limitCount = 1, secondTime = 10, rateLimiterType = RateLimiterType.REDIS, fallbackMethod = "fallbackMethod")
    public  String rateLimiter(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return name+"-ok";
    }

    public String fallbackMethod(String name) {
        return  name+"-fallback";
    }


    @ResponseBody
    @RequestMapping("/rate/no")

    public  String rateLimiterNo(HttpServletRequest request) {
        return "no";
    }
}

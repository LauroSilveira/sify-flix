package com.lauro.sifyflixapi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //@Before("execution(* com.lauro.sifyflixapi.restcontroller.SpaceshipRestController.getShipById.*.*(..))")
    public void logBeforeMethod() {
        log.warn("Method is about to be executed...");
    }
}

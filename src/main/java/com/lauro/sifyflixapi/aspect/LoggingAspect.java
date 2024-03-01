package com.lauro.sifyflixapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.lauro.sifyflixapi.restcontroller.spaceship.SpaceshipRestController.getShipById(..))")
    public void logPointcut() {
    }

    @Before("logPointcut()")
    public void logNegativeValues(JoinPoint joinPoint) {
        final var arg = Arrays.stream(joinPoint.getArgs())
                .findFirst()
                .map(Object::toString)
                .orElse(null);

        if (null != arg && Integer.parseInt(arg) < 1) {
            log.info("[LoggingAspect] - Logged Negative value: {}: ", joinPoint.getArgs());
        }
    }
}

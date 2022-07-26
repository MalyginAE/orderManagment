package com.nexign.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Aspect
public class LoggingServiceAspect {


    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer(){}



}

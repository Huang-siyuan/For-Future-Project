package com.magic.spring.AOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Slf4j
public class MethodExportAspect {

    @Around("@annotation(com.magic.spring.AOP.MethodExporter)")
    public Object methodExporter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long startTime = new Date().getTime();

        // Run the method and get the result
        Object proceed = proceedingJoinPoint.proceed();
        Long endTime = new Date().getTime();

        ObjectMapper objectMapper = new ObjectMapper();

        // Serialize the arguments to JSON
        String jsonParam = objectMapper.writeValueAsString(proceedingJoinPoint.getArgs());

        // Serialize the result to JSON
        String jsonResult = null;
        if (proceed != null) {
            jsonResult = objectMapper.writeValueAsString(proceed);
        } else {
            jsonResult = "null";
        }

        // Simulate report data
        log.info("Now is uploading report data to server:\n" +
                "target: {}.{}()\n" +
                "execution time: {}ms\n" +
                "parameters: {}\n" +
                "result: {}"
                ,proceedingJoinPoint.getTarget().getClass().getSimpleName()
                ,proceedingJoinPoint.getSignature().getName()
                ,(endTime - startTime)
                ,jsonParam
                ,jsonResult);

        return proceed;
    }

}

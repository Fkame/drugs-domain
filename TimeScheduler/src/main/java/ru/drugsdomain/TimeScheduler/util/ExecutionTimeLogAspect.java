package ru.drugsdomain.TimeScheduler.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ExecutionTimeLogAspect {

    @Around("@annotation(ru.drugsdomain.TimeScheduler.util.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint point) throws Throwable {
        try {
            var start = System.currentTimeMillis();
            var response = point.proceed();
            var end = System.currentTimeMillis();
            log.debug("class=[" + point.getSignature().getDeclaringTypeName() + "] method=[" +
                    point.getSignature().getName() +
                    "] proceeded in " +
                    (end - start) + " millis");
            return response;
        } catch (Throwable ex) {
            log.error("class=[" + point.getSignature().getDeclaringTypeName() + "] method=[" +
                    point.getSignature().getName() +
                    "] proceeded error: " +
                    ex.getMessage() + "\n" +
                    Arrays.toString(ex.getStackTrace()));
            throw ex;
        }
    }
}

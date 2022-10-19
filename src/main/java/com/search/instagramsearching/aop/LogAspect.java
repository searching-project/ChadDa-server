package com.search.instagramsearching.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect // Aspect 꼭 명시
@Log4j2
public class LogAspect {

//    private static final Logger ExecutionTimeLogger = LoggerFactory.getLogger("kafkaAppender-ExecutionTime");
    private static final Logger ExecutionTimeLogger = LoggerFactory.getLogger("stdout-json");

    // 특정 메서드에 대한 실행시간을 측정하기 위해 남겨보는 테스트용 코드
    @Around("@annotation(LogExecutionTime)")    // 해당 어노테이션에 대해
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinPoint.proceed();

        stopWatch.stop();
//        log.info(stopWatch.prettyPrint());
//        ExecutionTimeLogger.info("executionTime : {}", stopWatch.prettyPrint());
//        ExecutionTimeLogger.info(stopWatch.prettyPrint());
//        ExecutionTimeLogger.info(String.valueOf(stopWatch.getTotalTimeMillis()));
        ExecutionTimeLogger.info(stopWatch.toString());
        return proceed;
    }
}
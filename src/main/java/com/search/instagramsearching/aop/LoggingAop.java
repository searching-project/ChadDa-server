package com.search.instagramsearching.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

@RequiredArgsConstructor
@Aspect
@Component
public class LoggingAop {
    private static final Logger unitedLogger = LoggerFactory.getLogger("kafkaAppender");

    @Around("@annotation(com.search.instagramsearching.aop.ExecutionTimeLogging)")
    public Object executionTimeLog(ProceedingJoinPoint joinPoint) throws Throwable{

        // 실행 시간 측정
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinPoint.proceed();

        stopWatch.stop();
        String stopWatchStr = stopWatch.toString().split(";")[0].replace("StopWatch '': ", "");

        // 로그 남기기
        unitedLogger.info(getClassName(joinPoint) + " " + getMethodName(joinPoint) + " " + stopWatchStr + getParameter(joinPoint) + " ");

        return proceed;
    }

    // 실행시간 로그 생성하기
    private ExecutionTimeLog createExecutionTimeLog(JoinPoint joinPoint, String stopWatchStr) {
        ExecutionTimeLog timeLog = ExecutionTimeLog.builder()
                .className(getClassName(joinPoint))
                .methodName(getMethodName(joinPoint))
                .parameter(getParameter(joinPoint).toString())
                .executionTime(stopWatchStr)
                .build();
        return timeLog;
    }

    // 메소드명 가져오기
    public String getMethodName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getName();
    }

    // 클래스명 가져오기
    public String getClassName(JoinPoint joinPoint) {
        Class<?> activeClass = joinPoint.getTarget().getClass();
        return activeClass.getName();
    }

    // 매개변수 가져오기
    public String getParameter(JoinPoint joinPoint) {

        StringBuilder parameters = new StringBuilder();

        for (Object it : joinPoint.getArgs()) {
            parameters.append(" ");
            parameters.append(it.toString());
        }

        return parameters.toString();
    }
}

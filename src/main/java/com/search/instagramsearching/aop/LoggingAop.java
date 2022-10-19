package com.search.instagramsearching.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LoggingAop {
    Logger log = LoggerFactory.getLogger(LoggingAop.class);

    @Before(value = "@annotation(logging)", argNames="joinPoint, logging")
    public void log(JoinPoint joinPoint, Logging logging) throws Throwable{
        getValue(joinPoint, logging);
    }

    void getValue(JoinPoint joinPoint, Logging logging) {
        log.info(">>>>> " + getClassName(joinPoint, logging) + " / " + getMethodName(joinPoint) + " / " + getParameter(joinPoint));
    }

    // 메소드명 가져오기
    public String getMethodName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getName();
    }

    // 클래스명 가져오기
    public String getClassName(JoinPoint joinPoint, Logging logging) {
        Class<?> activeClass = joinPoint.getTarget().getClass();
        logging= activeClass.getAnnotation(Logging.class);
        return activeClass.getName();
    }

    // 매개변수 가져오기
    public List<String> getParameter(JoinPoint joinPoint) {
        List<String> parameterNames = new ArrayList<>();

        Stream.of(joinPoint.getArgs()).forEach(it -> {
            parameterNames.add(it.toString());
        });
        return parameterNames;
    }
}

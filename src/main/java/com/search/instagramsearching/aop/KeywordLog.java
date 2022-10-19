package com.search.instagramsearching.aop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class KeywordLog {
    private String className;
    private String methodName;
    private String parameter;

    @Override
    public String toString() {
        return "{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameter='" + parameter + '\'' +
                '}';    }
}

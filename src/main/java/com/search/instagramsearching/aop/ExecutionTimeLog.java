package com.search.instagramsearching.aop;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionTimeLog {
    private String className;
    private String methodName;
    private String parameter;
    private String executionTime;
}

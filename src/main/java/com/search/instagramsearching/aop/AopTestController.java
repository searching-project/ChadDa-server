package com.search.instagramsearching.aop;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class AopTestController {

    @GetMapping("/aop/loggingTest")
    @ExecutionTimeLogging
    public ResponseEntity<?> test() {

        String data = "테스트";

        return ResponseEntity.ok(data);
    }

}

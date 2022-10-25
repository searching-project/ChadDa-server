package com.search.instagramsearching.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j(topic = "kafka-logger")
@RequiredArgsConstructor
@RestController
public class KafkaTestController {

    @GetMapping({"", "/hello"})
    public ResponseEntity<?> hello() throws IOException {

        // kafka logger에 로그 남기기
        log.info("hello~!@");

        return ResponseEntity.ok("hello");
    }
}

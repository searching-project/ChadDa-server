package com.search.instagramsearching.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logging")
public class KafkaTestController {

//     // 방법 1. Logback 기본 Layout 활용해서 kafka에 전달
//     private static final Logger kafkaLogger = LoggerFactory.getLogger("kafkaLogger");
     private static final Logger kafkaLogger = LoggerFactory.getLogger("kafkaAppender");

//    // 방법 2. Logstash Layout 활용
//    private static final Logger kafkaLogger = LoggerFactory.getLogger("logstashKafkaAppender");

    @GetMapping("test")
    public void testLogging(@RequestParam(value = "name")String name) {
        kafkaLogger.info("name: {}", name);
    }
}

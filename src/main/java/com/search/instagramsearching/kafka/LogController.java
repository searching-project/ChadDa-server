package com.search.instagramsearching.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logging")
public class LogController {
    private static final Logger kafkaLogger = LoggerFactory.getLogger("kafkaLogger");
//    private LogProducer logProducer;

    @GetMapping("test")
    public void testLogging(@RequestParam(value = "name")String name) {
        kafkaLogger.info("name: {}", name);
//        logProducer();
    }
}

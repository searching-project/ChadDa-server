package com.search.instagramsearching.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

public class LogProducer {
//    public static void main(String[] args) throws IOException {     // main application ? => 좀 더 알아보기 ,,

    public LogProducer(String log) {
        Properties configs = new Properties();
        configs.put("bootstrap.servers", "localhost:9092");        // 브로커 지정 - 실무에서는 여러개 지정하기
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");        // 키 직렬화
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");      // 밸류 직렬화

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(configs);        // 프로듀서 인스턴스 생성 - 얘로 인해 데이터를 전송

//        ProducerRecord<String, String> record = new ProducerRecord<String, String>("log", "login");       // 토픽 지정 + 보낼 데이터 값 지정
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("log", log);       // 토픽 지정 + 보낼 데이터 값 지정

        // DB 연결 설정은 confids.put()으로? 혹은 다른 방법,, (아마 다른 방법 필요하지 않을까..)

        producer.send(record);

        producer.close();
    }

//    }
}
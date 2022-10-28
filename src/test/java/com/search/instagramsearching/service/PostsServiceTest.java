package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.request.PostRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class PostsServiceTest {
    @Test
    @Transactional
    public void testCreatePosts() throws InterruptedException{
        int numberOfThreads = 1000;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++){
            service.submit(() -> {
                PostsService.createPost(new PostRequestDto()).creater(10);
                latch.countDown();
            });
        }
    }
    latch.await();
    public class CountDownLatchT {
        int count = 1;
        public void call() {
            System.out.println("count = " + this.count++);
        }
    }
//    @Test
//    void CountDownLatch() throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//
//        CountDownLatchT tt = new CountDownLatchT();
//        for (int i = 1; i <= 5; i++) {
//            executorService.execute(() -> {
//                tt.call();
//            });
//        }
//
//        System.out.println("메인 스레드");
//    }
//
//    @Test
//    void CountDownLatch() throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        CountDownLatch countDownLatch = new CountDownLatch(5);
//
//        CountDownLatchT tt = new CountDownLatchT();
//        for (int i = 1; i <= 5; i++) {
//            executorService.execute(() -> {
//                tt.call();
//                countDownLatch.countDown();
//            });
//        }
//
//        countDownLatch.await();
//        System.out.println("메인 스레드");
//    }
}
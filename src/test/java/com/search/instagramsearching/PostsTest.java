//package com.search.instagramsearching;
//
//import com.search.instagramsearching.repository.PostsRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.util.StopWatch;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//public class PostsTest {
//    @Autowired
//    private PostsRepository postsRepository;
//
//    private String keyword;
//
//    @BeforeEach
//    void beforeAll(){keyword="dd";}
//
//    @Test
//    void testQuery(){
//        // given
//        StopWatch stopWatch = new StopWatch();
//        Pageable pageable = PageRequest.of(0, 10);
//
//        // when
//        stopWatch.start("검색 쿼리");
//        postsRepository.search(keyword,pageable);
//        stopWatch.stop();
//
//        // then
//        System.out.println(stopWatch.prettyPrint());
//    }
//
//    @Test
//    void testSearchViewQuery(){
//        // given
//        StopWatch stopWatch = new StopWatch();
//        Pageable pageable = PageRequest.of(0, 10);
//
//        // when
//        stopWatch.start("검색 쿼리");
//        postsRepository.searchView(keyword,pageable);
//        stopWatch.stop();
//
//        // then
//        System.out.println(stopWatch.prettyPrint());
//    }
//}

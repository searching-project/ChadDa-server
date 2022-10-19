package com.search.instagramsearching.service;

import com.search.instagramsearching.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StopWatch;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsersServiceTest {

    @Autowired
    private UsersRepository usersRepository;

    private String keyword;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        keyword = "john";    // 검색 테스트해볼 단어 입력
        pageable = Pageable.ofSize(20); // 페이지 설정
    }

    @DisplayName("FTS - Natural Mode")
    @Test
    void naturalSearch() {
        // given
        StopWatch stopWatch = new StopWatch();
        String original = keyword;
        keyword = "+"+keyword.replace(" "," +");

        // when
        stopWatch.start("ngram - natural mode");
        usersRepository.ngramNatualSearch(keyword, original, pageable);
        stopWatch.stop();

        // then
        System.out.println(stopWatch.prettyPrint());
    }

    @DisplayName("FTS - Boolean Mode")
    @Test
    void booleanSearch() {
        // given
        StopWatch stopWatch = new StopWatch();
        String original = keyword;
        keyword = "+"+keyword.replace(" "," +");

        // when
        stopWatch.start("ngram - boolean mode");
        usersRepository.ngramNatualSearch(keyword, original, pageable);
        stopWatch.stop();

        // then
        System.out.println(stopWatch.prettyPrint());
    }
}
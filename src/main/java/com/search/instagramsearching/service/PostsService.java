package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.response.PostResponseDto;
import com.search.instagramsearching.entity.Posts;
import com.search.instagramsearching.exception.ErrorCode;
import com.search.instagramsearching.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public List<Posts> search(String keyword, Pageable pageable){
        List<Posts> posts= postsRepository.search(keyword,pageable);
        return posts;
    }

    @Transactional
    public List<PostResponseDto> searchView(String keyword, Pageable pageable){
        List<PostResponseDto> posts= postsRepository.searchView(keyword,pageable);
        return posts;
    }

    public List<?> getUserPosts(Long userSid, Pageable pageable) {

        // profileId로 해당 posts 조회하기
        List<?> posts = getSearchResult(userSid, pageable);
        return posts;
    }

    // Index - profileId로 posts table 조회
    private List<?> getSearchResult(Long profileId, Pageable pageable) {
        List<PostResponseDto> searchResult = postsRepository.getUserPosts(profileId, pageable);
        if (searchResult.size() == 0) {

            // 방법 1 : 정석 예외처리
//            throw new PostsNotFoundExceptioin();

//            // 방법 2 : 임시 예외처리 - response 조작
//            List<String> str_response = new ArrayList<>();
//            str_response.add(ErrorCode.RESULT_NOT_FOUND.getMessage());
//            return str_response;

            // 방법 3 : 그냥 냅다 보내기
            return searchResult;
        }
        return searchResult;
    }
}

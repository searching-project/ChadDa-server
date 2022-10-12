package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.request.PostRequestDto;
import com.search.instagramsearching.dto.response.PostResponseDto;
import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.dto.response.UserPostSearchResultDto;
import com.search.instagramsearching.dto.response.UserPostsResponseDto;
import com.search.instagramsearching.entity.Posts;
import com.search.instagramsearching.exception.PostsNotFoundExceptioin;
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

    public List<PostResponseDto> getUserPosts(Long userSid, Pageable pageable) {

        // profileId로 해당 posts 조회하기
        List<PostResponseDto> posts = getSearchResult(userSid, pageable);
        return posts;
    }

    // Index - profileId로 posts table 조회
    private List<PostResponseDto> getSearchResult(Long profileId, Pageable pageable) {
        List<PostResponseDto> searchResult = postsRepository.getUserPosts(profileId, pageable);
        if (searchResult.size() == 0) {
            throw new PostsNotFoundExceptioin();
        }
        return searchResult;
    }

    public ResponseDto<?> createPost(PostRequestDto requestDto) {

        return ResponseDto.success();
    }
}

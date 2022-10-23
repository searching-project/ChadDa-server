package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.response.PostResponseDto;
import com.search.instagramsearching.entity.Posts;
import com.search.instagramsearching.exception.ErrorCode;
import com.search.instagramsearching.exception.NotFoundException;
import com.search.instagramsearching.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new NotFoundException(ErrorCode.RESULT_NOT_FOUND);
        }
        return searchResult;
    }
}

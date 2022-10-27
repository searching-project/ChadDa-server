package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.request.PostRequestDto;
import com.search.instagramsearching.dto.response.PostResponseDto;
import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.dto.response.UserPostSearchResultDto;
import com.search.instagramsearching.dto.response.UserPostsResponseDto;
import com.search.instagramsearching.entity.Posts;
import com.search.instagramsearching.exception.ErrorResponse;
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
import java.util.Optional;

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
            throw new NotFoundException(ErrorCode.RESULT_NOT_FOUND);

//            // 방법 2 : 임시 예외처리 - response 조작
//            List<String> str_response = new ArrayList<>();
//            str_response.add(ErrorCode.RESULT_NOT_FOUND.getMessage());
//            return str_response;

            // 방법 3 : 그냥 냅다 보내기
        }
        return searchResult;
    }
    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto) {
        Posts post = new Posts(requestDto);
        postsRepository.save(post);
        return ResponseDto.success(post);
    }

    @Transactional
    public ResponseDto<?> updatePost(Long sid, PostRequestDto requestDto) {
        Posts posts = isPresentPost(sid);
//        if(posts == null){
//            ErrorResponse.builder();
//            return ResponseDto.fail("POST_NOT_FOUND");
//
//        }
        return ResponseDto.success("fe");
    }
    public Posts isPresentPost(Long id) {
        Optional<Posts> optionalComment = postsRepository.findById(id);
        return optionalComment.orElse(null);
    }
}
package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.response.PostResponseDto;
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

    public List<UserPostsResponseDto> getUserPosts(Long userSid, Pageable pageable) {

        // profileId로 해당 posts 조회하기
        List<UserPostSearchResultDto> postsList = getSearchResult(userSid, pageable);
        List<UserPostsResponseDto> response = new ArrayList<>();
        for (UserPostSearchResultDto postInfo : postsList) {
            response.add(
                    UserPostsResponseDto.builder()
                            .profileId(postInfo.getProfile_id())
                            .profileName(null)
                            .postId(postInfo.getPost_id())
                            .locationId(postInfo.getLocation_id())
                            .description(postInfo.getDescription())
                            .cts(postInfo.getCts())
                            .postType(postInfo.getPost_type())
                            .numberLikes(postInfo.getNumber_likes())
                            .numberComments(postInfo.getNumber_comments())
                            .build()
            );
        }
        return response;
    }

    // Index - profileId로 posts table 조회
    private List<UserPostSearchResultDto> getSearchResult(Long profileId, Pageable pageable) {
        List<UserPostSearchResultDto> searchResult = postsRepository.getUserPosts(profileId, pageable);
        if (searchResult.size() == 0) {
            throw new PostsNotFoundExceptioin();
        }
        return searchResult;
    }
}

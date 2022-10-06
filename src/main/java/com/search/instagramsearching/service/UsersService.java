package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.response.UserPostSearchResultDto;
import com.search.instagramsearching.dto.response.UserPostsResponseDto;
import com.search.instagramsearching.dto.response.UserResponseDto;
import com.search.instagramsearching.dto.response.UserSearchResultDto;
import com.search.instagramsearching.entity.Users;
import com.search.instagramsearching.exception.PostsNotFoundExceptioin;
import com.search.instagramsearching.exception.ResultNotFoundException;
import com.search.instagramsearching.exception.UserNotFoundException;
import com.search.instagramsearching.repository.PostsRepository;
import com.search.instagramsearching.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public List<?> searchUsers(String keyword, Pageable pageable) {

        // 키워드에 맞는 검색 결과 받아오기
        List<UserSearchResultDto> rawDataList = usersRepository.searchUsers(keyword, pageable);
        if (rawDataList == null || rawDataList.size() == 0) {
            throw new ResultNotFoundException();
        }

        // 검색결과를 ResponseDto에 담기
        List<UserResponseDto> searchResultList = new ArrayList<>();
        for (UserSearchResultDto rawData : rawDataList) {
            searchResultList.add(
                    UserResponseDto.builder()
                            .sid(rawData.getSid())
                            .profileName(rawData.getProfile_name())
                            .businessAccountTf(rawData.getBusiness_account_tf())
                            .firstnameLastname(rawData.getFirstname_lastname())
                            .profileId(rawData.getProfile_id())
                            .nPosts(rawData.getN_posts())
                            .following(rawData.getFollowing())
                            .followers(rawData.getFollowers())
                            .description(rawData.getDescription())
                            .url(rawData.getUrl())
                            .build()
            );
        }
        return searchResultList;
    }

    public List<UserPostsResponseDto> getUserPosts(Long userSid, Pageable pageable) {

//        // profileName으로 profileId 찾기
//        Long profileId = getProfileId(profileName, pageable);

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

    // fulltext - profileNamed으로 users table 조회
    private Long getProfileId(String profileName, Pageable pageable) {
        List<UserSearchResultDto> searchResult = usersRepository.searchUsers(profileName, pageable);
        if (searchResult.size() == 0) {
            throw new UserNotFoundException();
        }

        Long profileId = searchResult.get(0).getProfile_id();
        return profileId;
    }

    // Index - profileId로 posts table 조회
    private List<UserPostSearchResultDto> getSearchResult(Long profileId, Pageable pageable) {
        List<UserPostSearchResultDto> searchResult = postsRepository.getUserPosts(profileId, pageable);
        if (searchResult.size() == 0) {
            throw new PostsNotFoundExceptioin();
        }
        return searchResult;
    }

    @Transactional
    public UserResponseDto findUserBySID (Long sid){
        Optional<Users> usersOptional = usersRepository.findUsersBySid(sid);
        Users user = new Users();
        if (usersOptional.isPresent()) {
            user = usersOptional.get();
        }

        return UserResponseDto.builder()
                .sid(user.getSid())
                .profileName(user.getProfileName())
                .businessAccountTf(user.getBusinessAccountTf())
                .firstnameLastname(user.getFirstnameLastname())
                .profileId(user.getProfileId())
                .nPosts(user.getNPosts())
                .following(user.getFollowing())
                .followers(user.getFollowers())
                .description(user.getDescription())
                .url(user.getUrl())
                .build();
    }
}
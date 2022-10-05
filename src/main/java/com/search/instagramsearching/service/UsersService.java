package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.response.UserPostsResponseDto;
import com.search.instagramsearching.dto.response.UserResponseDto;
import com.search.instagramsearching.dto.response.UserSearchResultDto;
import com.search.instagramsearching.entity.Posts;
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
        List<UserSearchResultDto> rawDataList = usersRepository.searchUsers(keyword, pageable);
        if (rawDataList == null || rawDataList.size() == 0) {
            throw new ResultNotFoundException();
        }

        List<UserResponseDto> searchResultList = new ArrayList<>();

        for (UserSearchResultDto rawData : rawDataList) {
            searchResultList.add(
                    UserResponseDto.builder()
                            .id(rawData.getId())
                            .sid(rawData.getSid())
                            .profileName(rawData.getProfile_name())
                            .businessAccountTF(rawData.getBusiness_account_tf())
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

    public List<?> getUserPosts(String profileName, Pageable pageable) {

        // findBy로 조회 -> 속도가 느려 삭제
//        Users user = isPresentProfileName(profileName);
//        if (user == null) {
//            throw new UserNotFoundException();
//        }

        Long profileId = getProfileId(profileName);

        List<Posts> postsList = postsRepository.findByProfileId(profileId);
        if (postsList.size() == 0) {
            throw new PostsNotFoundExceptioin();
        }

        List<UserPostsResponseDto> response = new ArrayList<>();
        for (Posts post : postsList) {
            response.add(
                    UserPostsResponseDto.builder()
                            .profileId(post.getProfileId())
                            .postId(post.getPostId())
                            .locationId(post.getLocationId())
                            .description(post.getDescription())
                            .cts(post.getCts())
                            .postType(post.getPostType())
                            .numberLikes(post.getNumbrLikes())
                            .numberComments(post.getNumberComments())
                            .build()
            );
        }
        return response;
    }

    // findBy로 조회 -> 검색 속도가 너무 느려 삭제
    private Users isPresentProfileName(String profileName) {
        Optional<Users> user = usersRepository.findByProfileName(profileName);
        return user.orElse(null);
    }

    private Long getProfileId(String profileName) {
        Pageable pageable = Pageable.ofSize(1);

        List<UserSearchResultDto> searchResult = usersRepository.searchUsers(profileName,pageable);
        if (searchResult.size() == 0) {
            throw new UserNotFoundException();
        }
        Long profileId = searchResult.get(0).getProfile_id();

        return profileId;
    }
}
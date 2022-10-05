package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.dto.response.UserResponseDto;
import com.search.instagramsearching.dto.response.UserSearchResultDto;
import com.search.instagramsearching.exception.ResultNotFoundException;
import com.search.instagramsearching.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;

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
}
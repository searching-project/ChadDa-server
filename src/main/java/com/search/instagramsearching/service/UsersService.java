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
    public List<?> naturalSearch(String keyword, Pageable pageable) {
        String original = keyword;
        keyword = getAnd(keyword);

        List<UserSearchResultDto> rawDataList = usersRepository.ngramNatualSearch(keyword, original, pageable);
        if (rawDataList == null || rawDataList.size() == 0) {
            throw new ResultNotFoundException();
        }

        List<UserResponseDto> searchResultList = new ArrayList<>();

        for (UserSearchResultDto rawData : rawDataList) {
            searchResultList.add(
                    UserResponseDto.builder()
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

    @Transactional
    public List<?> booleanSearch(String keyword, Pageable pageable) {
        String original = keyword;
        keyword = getAnd(keyword);

        List<UserSearchResultDto> rawDataList = usersRepository.ngramBooleanSearch(keyword, original, pageable);
        if (rawDataList == null || rawDataList.size() == 0) {
            throw new ResultNotFoundException();
        }

        List<UserResponseDto> searchResultList = new ArrayList<>();

        for (UserSearchResultDto rawData : rawDataList) {
            searchResultList.add(
                    UserResponseDto.builder()
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

    // "검색어1" AND "검색어2"가 모두 포함된 데이터를 조회
    private String getAnd(String str) {
        return "+"+str.replace(" "," +");
    }
}
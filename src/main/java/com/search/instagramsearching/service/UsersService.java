package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.request.LoginReqDto;
import com.search.instagramsearching.dto.request.SignupRequestDto;
import com.search.instagramsearching.dto.response.*;
import com.search.instagramsearching.entity.RefreshToken;
import com.search.instagramsearching.entity.Users;
import com.search.instagramsearching.exception.ErrorCode;
import com.search.instagramsearching.exception.ResultNotFoundException;
import com.search.instagramsearching.jwt.util.JwtUtil;
import com.search.instagramsearching.jwt.util.TokenProperties;
import com.search.instagramsearching.repository.PostsRepository;
import com.search.instagramsearching.repository.RefreshTokenRedisRepository;
import com.search.instagramsearching.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsersService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    private final RefreshTokenRedisRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

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

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        // 회원 ID 중복 확인
        Optional<Users> found = usersRepository.findByProfileName(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        String name = requestDto.getName();
        Users user = new Users(username, password,name);
        usersRepository.save(user);
    }
    @Transactional()
    public Users isPresentUsersByUsername(String username) {
        Optional<Users> optionalUsers = usersRepository.findByProfileName(username);
        return optionalUsers.orElse(null);
    }

    private void TokenToHeaders(HttpServletResponse response, String accessToken, String refreshToken) {
        response.addHeader(TokenProperties.AUTH_HEADER, TokenProperties.TOKEN_TYPE + accessToken);
        response.addHeader(TokenProperties.REFRESH_HEADER, TokenProperties.TOKEN_TYPE + refreshToken);
    }

}
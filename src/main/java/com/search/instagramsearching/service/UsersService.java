package com.search.instagramsearching.service;

import com.search.instagramsearching.aop.ExecutionTimeLogging;
import com.search.instagramsearching.dto.response.UserResponseDto;
import com.search.instagramsearching.dto.response.UserSearchResultDto;
import com.search.instagramsearching.entity.Users;
import com.search.instagramsearching.exception.ResultNotFoundException;
import com.search.instagramsearching.dto.request.LoginReqDto;
import com.search.instagramsearching.dto.request.SignupRequestDto;
import com.search.instagramsearching.dto.response.*;
import com.search.instagramsearching.entity.RefreshToken;
import com.search.instagramsearching.entity.Users;
import com.search.instagramsearching.exception.ErrorCode;
import com.search.instagramsearching.exception.NotFoundException;
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

    @ExecutionTimeLogging
    private final RefreshTokenRedisRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public List<?> searchUsers(String keyword, Pageable pageable) {

        // 키워드에 맞는 검색 결과 받아오기
        List<UserSearchResultDto> rawDataList = usersRepository.searchUsers(keyword, pageable);
        if (rawDataList == null || rawDataList.size() == 0) {

            // 방법 1 : 정석 - 예외처리
            throw new NotFoundException(ErrorCode.RESULT_NOT_FOUND);

//            // 방법 2 : 임시 response 보내기 -> 이유는 모르겠지만 NPE로 실패
//            List<String> str_response = new ArrayList<>();
//            str_response.add(ErrorCode.RESULT_NOT_FOUND.getMessage());
//            return str_response;

            // 방법 3 : 그냥 빈 리스트 보내기
            return rawDataList;
//
//            // 방법 4 :  UserSearchResultDto 보내기
//            List<UserResponseDto> result = new ArrayList<>();
//                result.add(
//                        UserResponseDto.builder().sid(null).profileName("").businessAccountTf(false).firstnameLastname("").profileId(0L).nPosts(null).following(0).followers(0)
//                                .description(ErrorCode.RESULT_NOT_FOUND.getMessage()).url("").build()
//                );
//            return result;
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
    @Transactional
    public ResponseDto<?> login(LoginReqDto loginReqDto, HttpServletResponse response){
        String username = loginReqDto.getUsername();
        Users user = isPresentUsersByUsername(username);

        if(user == null){
            return ResponseDto.fail(ErrorCode.USER_NOT_FOUND);
        }

        if(!user.validatePassword(passwordEncoder,loginReqDto.getPassword())){
            return ResponseDto.fail(ErrorCode.USER_NOT_FOUND);
        }

        // 토큰 발급
        String accessToken = jwtUtil.createToken(username,TokenProperties.AUTH_HEADER);
        String refreshToken = jwtUtil.createToken(username, TokenProperties.REFRESH_HEADER);

        RefreshToken refreshTokenFromDB = jwtUtil.getRefreshTokenFromDB(username);

        // 로그인 경력이 있는 사용자 -> DB에 Refresh Token 있음 -> 새로 로그인 했으면 새로 발급받는 토큰으로 변경
        // 로그인이 처음인 사용자 -> DB에 Refresh Token 없음 -> 발급받은 Refresh 토큰 저장
        if(refreshTokenFromDB == null){
            RefreshToken saveRefreshToken = RefreshToken.createRefreshToken(username,refreshToken,TokenProperties.REFRESH_TOKEN_VALID_TIME);

            refreshTokenRepository.save(saveRefreshToken);

        }else{
            refreshTokenFromDB.updateValue(refreshToken);
            refreshTokenRepository.save(refreshTokenFromDB);
        }

        // 헤더에 응답으로 보내줌
        TokenToHeaders(response, accessToken, refreshToken);

        LoginResDto loginResDto = LoginResDto.builder()
                .id(user.getSid())
                .username(username)
                .build();
        return ResponseDto.success(loginResDto);
    }

    @Transactional
    public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refreshHeader = request.getHeader(TokenProperties.REFRESH_HEADER);

        if (refreshHeader == null) {
            return ResponseDto.fail(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        if (!refreshHeader.startsWith(TokenProperties.TOKEN_TYPE)) {
            return ResponseDto.fail(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String refreshToken = refreshHeader.replace(TokenProperties.TOKEN_TYPE, "");


        // Refresh 토큰 검증
        String refreshTokenValidate = jwtUtil.validateToken(refreshToken);

        switch (refreshTokenValidate) {
            case TokenProperties.EXPIRED:
                return ResponseDto.fail(ErrorCode.EXPIRED_REFRESH_TOKEN);
            case TokenProperties.VALID:
                String username = jwtUtil.getUsernameFromToken(refreshToken);
                Users user = isPresentUsersByUsername(username);

                if (user == null) {
                    return ResponseDto.fail(ErrorCode.USER_NOT_FOUND);
                } else {
                    RefreshToken refreshTokenFromDB = jwtUtil.getRefreshTokenFromDB(user.getProfileName());
                    if (refreshTokenFromDB != null && refreshToken.equals(refreshTokenFromDB.getTokenValue())) {
                        String newAccessToken = jwtUtil.createToken(username, TokenProperties.AUTH_HEADER);
                        // 헤더에 응답으로 보내줌
                        TokenToHeaders(response, newAccessToken, refreshToken);
                        MessageDto messageDto = MessageDto.builder()
                                .message("Access Token이 발급되었습니다.")
                                .build();
                        return ResponseDto.success(messageDto);
                    } else {
                        return ResponseDto.fail(ErrorCode.INVALID_REFRESH_TOKEN);
                    }
                }
            default:
                return ResponseDto.fail(ErrorCode.INVALID_REFRESH_TOKEN);
        }
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
package com.search.instagramsearching.jwt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.instagramsearching.CacheKey;
import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.entity.RefreshToken;
import com.search.instagramsearching.entity.Users;
import com.search.instagramsearching.exception.ErrorCode;
import com.search.instagramsearching.repository.RefreshTokenRedisRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final RefreshTokenRedisRepository refreshTokenRepository;
    private final ObjectMapper objectMapper;

    @Value("${jwt.secret.key}")
    private String secretKey;
    Key key;
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }


    // 토큰 생성
    public String createToken(String username, String type){
        Date date = new Date();
        long time = type.equals(TokenProperties.AUTH_HEADER)? TokenProperties.ACCESS_TOKEN_VALID_TIME : TokenProperties.REFRESH_TOKEN_VALID_TIME;

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(key,signatureAlgorithm)
                .compact();
    }

    public String validateToken( String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return TokenProperties.VALID;
        } catch (ExpiredJwtException e) {
            return TokenProperties.EXPIRED;
        } catch ( JwtException | IllegalArgumentException | NullPointerException e) {
            return TokenProperties.INVALID;
        }
    }

    // 예외 응답
    public void exceptionResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseDto<?> responseDto = ResponseDto.fail(errorCode);
        String httpResponse = objectMapper.writeValueAsString(responseDto);
        response.getWriter().write(httpResponse);
    }


    // DB에 있는 refreshToken가져오기
    @Cacheable(value = CacheKey.REFRESH, key = "#userName", unless = "#result == null")
    public RefreshToken getRefreshTokenFromDB(String userName){
        Optional<RefreshToken> refreshTokenFromDB = refreshTokenRepository.findById(userName);
        return refreshTokenFromDB.orElse(null);
    }

    // token에서 payload 권한 값 중 username 가져오기
    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

}

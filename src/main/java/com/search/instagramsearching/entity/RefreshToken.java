package com.search.instagramsearching.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@RedisHash(value = "refreshToken")
@AllArgsConstructor
@Builder
public class RefreshToken implements Serializable {

    @Id
    private String id;

    private String tokenValue;

    @TimeToLive
    private Long expiration;

    public static RefreshToken createRefreshToken(String username, String refreshToken, Long remainingMilliSeconds) {
        return RefreshToken.builder()
                .id(username)
                .tokenValue(refreshToken)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }

    public void updateValue(String tokenValue){
        this.tokenValue = tokenValue;
    }
}

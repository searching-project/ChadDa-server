package com.search.instagramsearching.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RefreshToken {

    @Id
    private String id;

    @Column
    private String tokenValue;

    @Column
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

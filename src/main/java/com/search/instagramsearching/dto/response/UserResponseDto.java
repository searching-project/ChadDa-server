package com.search.instagramsearching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long sid;
    private Long profileId;
    private String profileName;
    private String firstnameLastname;
    private String description;
    private Integer following;
    private Integer followers;
    private Integer nPosts;
    private String url;
    private Boolean businessAccountTf;
}
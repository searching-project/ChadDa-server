package com.search.instagramsearching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UserSearchResponseDto {
    private Long sid;
    private String profileName;
    private String isBusinessAccount;
    private String firstnameLastname;
    private Long profileId;
    private Integer nPosts;
    private Integer following;
    private Integer followers;
    private String description;
    private String url;
}
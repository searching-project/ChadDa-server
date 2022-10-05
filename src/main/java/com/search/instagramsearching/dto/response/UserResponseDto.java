package com.search.instagramsearching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private Long sid;
    private String profileName;
    private String businessAccountTf;
    private String firstnameLastname;
    private Long profileId;
    private Integer nPosts;
    private Integer following;
    private Integer followers;
    private String description;
    private String url;
}
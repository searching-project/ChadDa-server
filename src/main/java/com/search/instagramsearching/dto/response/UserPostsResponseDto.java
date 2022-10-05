package com.search.instagramsearching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
public class UserPostsResponseDto {
    private Long profileId;
    private String postId;
    private Long locationId;
    private String description;
    private Date cts;
    private Integer postType;
    private Integer numberLikes;
    private Integer numberComments;

}

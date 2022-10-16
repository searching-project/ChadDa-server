package com.search.instagramsearching.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDto {
    private String description;
    private Long sidProfile;
    private String postId;
    private Long profileId;
    private Long locationId;
    private Date cts;
    private int postType;
    private Integer numbrLikes;
    private Integer numberComments;
}

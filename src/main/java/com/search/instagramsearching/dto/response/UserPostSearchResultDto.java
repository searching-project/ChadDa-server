package com.search.instagramsearching.dto.response;

import java.util.Date;

public interface UserPostSearchResultDto {
    Long getProfile_id();
    String getPost_id();
    Long getLocation_id();
    String getDescription();
    Date getCts();
    Integer getPost_type();
    Integer getNumber_likes();
    Integer getNumber_comments();
}
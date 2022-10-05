package com.search.instagramsearching.dto.response;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public interface PostResponseDto {
    Long getSid();
    Long getSid_profile();
    String getProfile_name();
    Long getLocation_id();
    String getName();
    Date getCts();
    int getPost_type();
    String getDescription();
    Integer getNumbr_likes();
    Integer getNumber_comments();
}

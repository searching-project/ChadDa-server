package com.search.instagramsearching.dto.response;

public interface UserSearchResultDto {
    Long getSid();
    Long getProfile_id();
    String getProfile_name();
    String getFirstname_lastname();
    String getDescription();
    Integer getFollowing();
    Integer getFollowers();
    Integer getN_posts();
    String getUrl();
    Boolean getBusiness_account_tf();
}
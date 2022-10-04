package com.search.instagramsearching.dto.response;

public interface UserSearchResultDto {
    Long getSid();
    String getProfile_name();
    String getBusiness_account_tf();
    String getFirstname_lastname();
    Long getProfile_id();
    Integer getN_posts();
    Integer getFollowing();
    Integer getFollowers();
    String getDescription();
    String getUrl();
}
package com.search.instagramsearching.repository;

import com.search.instagramsearching.dto.response.LocationResResultDto;

import com.search.instagramsearching.dto.response.UserPostSearchResultDto;
import com.search.instagramsearching.dto.response.PostResponseDto;
import com.search.instagramsearching.entity.Posts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import javax.persistence.LockModeType;
import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // locationId로 검색 -> JPA 문법 사용
    @Lock(LockModeType.OPTIMISTIC)
    List<Posts> findAllByLocationId(Long locationId);


    @Query(value = "select * FROM posts as p WHERE MATCH(p.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)"
            ,countQuery ="select count(*) from posts as p WHERE MATCH(p.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",nativeQuery = true )
    List<Posts> search(@Param("keyword")String keyword, @PageableDefault Pageable pageable);

    @Query(value = "select * FROM search_post as p WHERE MATCH(p.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)"
            ,countQuery ="select count(*) from search_post as p WHERE MATCH(p.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",nativeQuery = true )
    List<PostResponseDto> searchView(@Param("keyword")String keyword, @PageableDefault Pageable pageable);

    /*
    // version 1 : view 미적용 - 유저 profileId로 게시글 검색하기
    @Query(value = "SELECT profile_id, post_id, location_id, description, cts, post_type, number_likes, number_comments FROM posts\n" +
            "WHERE sid_profile = :userSid",
            countQuery = "SELECT count(*) FROM posts WHERE sid_profile = :userSid",
            nativeQuery = true)
    List<UserPostSearchResultDto> getUserPosts(Long userSid, @PageableDefault Pageable pageable);
    */

    // version 2 : view 적용 - 유저 profileId로 게시글 검색하기
    @Query(value = "select * FROM search_post as p WHERE p.sid_profile = :userSid",
            countQuery ="select count(*) FROM search_post as p WHERE p.sid_profile = :userSid",
            nativeQuery = true )
    List<PostResponseDto> getUserPosts(@Param("userSid")Long userSid, @PageableDefault Pageable pageable);
}
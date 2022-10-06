package com.search.instagramsearching.repository;

import com.search.instagramsearching.dto.response.UserPostSearchResultDto;
import com.search.instagramsearching.dto.response.PostResponseDto;
import com.search.instagramsearching.entity.Posts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

/*
    @Query(value = "select * FROM posts as p WHERE MATCH(p.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)"
            ,countQuery ="select count(*) from posts as p WHERE MATCH(p.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",nativeQuery = true )
    List<Posts> search(@Param("keyword")String keyword, @PageableDefault Pageable pageable);

    @Query(value = "select * FROM search_post as p WHERE MATCH(p.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)"
            ,countQuery ="select count(*) from search_post as p WHERE MATCH(p.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",nativeQuery = true )
    List<PostResponseDto> searchView(@Param("keyword")String keyword, @PageableDefault Pageable pageable);
*/

    // 유저 profileId로 게시글 검색하기
    @Query(value = "SELECT profile_id, post_id, location_id, description, cts, post_type, number_likes, number_comments FROM posts\n" +
            "WHERE sid_profile = :userSid",
            countQuery = "SELECT count(*) FROM posts WHERE sid_profile = :userSid",
            nativeQuery = true)
    List<UserPostSearchResultDto> getUserPosts(Long userSid, @PageableDefault Pageable pageable);
}
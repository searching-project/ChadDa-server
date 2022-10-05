package com.search.instagramsearching.repository;

import com.search.instagramsearching.dto.response.UserPostSearchResultDto;
import com.search.instagramsearching.entity.Posts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findByProfileId(Long profileId);

    @Query(value = "SELECT profile_id, post_id, location_id, description, cts, post_type, number_likes, number_comments FROM posts\n" +
            "WHERE profile_id = :profileId",
            countQuery = "SELECT count(*) FROM posts WHERE profile_id = :profileId",
            nativeQuery = true)
    List<UserPostSearchResultDto> getUserPosts(Long profileId, @PageableDefault Pageable pageable);
}
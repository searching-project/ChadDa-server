package com.search.instagramsearching.repository;

import com.search.instagramsearching.dto.response.LocationResResultDto;
import com.search.instagramsearching.entity.Posts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query(value = "select L.sid, L.name, L.city, L.region, L.street" +
            " from instagram_locations as L where match(name, region) against(:keyword IN BOOLEAN MODE)",
            countQuery = "select count(*) from instagram_locations where match (name, region) against(:keyword IN BOOLEAN MODE)",
            nativeQuery = true)
    List<LocationResResultDto> serachPostsFromLocation(@Param("keyword") String keyword, @PageableDefault Pageable pageable);
}

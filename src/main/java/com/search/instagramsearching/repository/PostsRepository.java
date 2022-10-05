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
    List<Posts> findAllByLocationId(Long locationId);
}
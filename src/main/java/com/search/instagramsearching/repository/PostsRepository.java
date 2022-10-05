package com.search.instagramsearching.repository;

import com.search.instagramsearching.entity.Posts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findByProfileId(Long profileId);
}

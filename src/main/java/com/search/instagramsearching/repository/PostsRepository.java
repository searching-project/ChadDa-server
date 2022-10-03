package com.search.instagramsearching.repository;

import com.search.instagramsearching.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
}

package com.search.instagramsearching.repository;

import com.search.instagramsearching.entity.Posts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query(value = "select * FROM posts as p WHERE MATCH(p.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)"
            ,countQuery ="select count(*) from posts as p WHERE MATCH(p.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",nativeQuery = true )
    List<Posts> search(@Param("keyword")String keyword, @PageableDefault Pageable pageable);
}

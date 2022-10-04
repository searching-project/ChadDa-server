package com.search.instagramsearching.repository;

import com.search.instagramsearching.dto.response.UserSearchResultDto;
import com.search.instagramsearching.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Long> {

    // full text search - natural mode 검색
    @Query(value = "SELECT sid, profile_name, business_account_tf, firstname_lastname, profile_id, n_posts, following, followers, description, url FROM users u\n" +
            "WHERE MATCH (profile_name, firstname_lastname, description) AGAINST (:keyword IN NATURAL LANGUAGE MODE)\n" +
            "ORDER BY CASE WHEN u.firstname_lastname = :original then 1 WHEN u.profile_name = :original then 2 WHEN u.description = :original then 3 ELSE 4 END",
            countQuery = "SELECT count(*) FROM users u WHERE MATCH (profile_name, firstname_lastname, description) AGAINST (:keyword IN NATURAL LANGUAGE MODE)",
            nativeQuery = true)
    List<UserSearchResultDto> ngramNatualSearch(@Param("keyword")String keyword, String original, @PageableDefault Pageable pageable);

    // full text search - boolean mode 검색
    @Query(value = "SELECT sid, profile_name, business_account_tf, firstname_lastname, profile_id, n_posts, following, followers, description, url FROM users u\n" +
            "WHERE MATCH (profile_name, firstname_lastname, description) AGAINST (:keyword IN BOOLEAN MODE)\n" +
            "ORDER BY CASE WHEN u.firstname_lastname = :original then 1 WHEN u.profile_name = :original then 2 WHEN u.description = :original then 3 ELSE 4 END",
            countQuery = "SELECT count(*) FROM users u WHERE MATCH (profile_name, firstname_lastname, description) AGAINST (:keyword IN BOOLEAN MODE)",
            nativeQuery = true)
    List<UserSearchResultDto> ngramBooleanSearch(@Param("keyword")String keyword, String original, @PageableDefault Pageable pageable);
}
package com.search.instagramsearching.repository;

import com.search.instagramsearching.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Long> {

    // full text search - natural mode 검색
    @Query(value = "SELECT * FROM users WHERE MATCH (profile_name, firstname_lastname, description)"
            + "AGAINST (:keyword IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    // Page<Users> ngramSearch(@Param("keyword")String keyword, Pageable pageable);
    List<Users> ngramNatualSearch(@Param("keyword")String keyword, @PageableDefault Pageable pageable);

    // full text search - boolean mode 검색
    @Query(value = "SELECT * FROM users WHERE MATCH (profile_name, firstname_lastname, description)"
            + "AGAINST (:keyword IN BOOLEAN MODE)", nativeQuery = true)
    // Page<Users> ngramSearch(@Param("keyword")String keyword, Pageable pageable);
    List<Users> ngramBooleanSearch(@Param("keyword")String keyword, @PageableDefault Pageable pageable);

}
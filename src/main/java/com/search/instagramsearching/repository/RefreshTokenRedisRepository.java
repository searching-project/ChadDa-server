package com.search.instagramsearching.repository;

import com.search.instagramsearching.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends JpaRepository<RefreshToken, String> {
}

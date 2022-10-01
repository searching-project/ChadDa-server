package com.search.instagramsearching.repository;

import com.search.instagramsearching.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository  extends JpaRepository<Users,Long> {
}

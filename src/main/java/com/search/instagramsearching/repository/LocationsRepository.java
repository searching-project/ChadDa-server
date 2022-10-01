package com.search.instagramsearching.repository;

import com.search.instagramsearching.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationsRepository  extends JpaRepository<Locations,Long> {
}

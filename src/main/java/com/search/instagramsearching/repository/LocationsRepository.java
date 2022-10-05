package com.search.instagramsearching.repository;

import com.search.instagramsearching.dto.response.LocationResResultDto;
import com.search.instagramsearching.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationsRepository  extends JpaRepository<Locations,Long> {
    @Query(value = "select L.name, L.city.L.region.L.street" +
            " from instagram.instagram_locations as L where match(name) against(:keyword IN BOOLEAN MODE)", countQuery = ,nativeQuery = true )
    List<LocationResResultDto> serarchLocation(@Param("keyword") String keyword);
}

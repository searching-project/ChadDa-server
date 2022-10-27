package com.search.instagramsearching.controller;

import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.service.LocationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class LocationsController {
    private final LocationsService locationsService;

    @GetMapping("/search/location/{keyword}")
    public ResponseDto<?> searchLocations(@PathVariable String keyword, @PageableDefault(page = 0, size = 20) Pageable pageable){
        return locationsService.searchLocations(keyword,pageable);
    }
    @GetMapping("/search/location/{locationid}/post")
    public ResponseDto<?> searchPostsFromLocations(@PathVariable Long locationid,@PageableDefault(page = 0, size = 20) Pageable pageable){
        System.out.println("메롱");
        return locationsService.searchPostsFromLocations(locationid, pageable);
    }
}

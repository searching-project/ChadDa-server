package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.response.LocationResDto;
import com.search.instagramsearching.dto.response.LocationResResultDto;
import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.exception.ResultNotFoundException;
import com.search.instagramsearching.repository.LocationsRepository;
import com.search.instagramsearching.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocationsService {
    private final LocationsRepository locationsRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public ResponseDto<?> searchLocations(String keyword, Pageable pageable) {
        List<LocationResResultDto> locationResResultDtoList = locationsRepository.searchLocation(keyword, pageable);
        List<LocationResDto> locationResDtoList = new ArrayList<>();
        for(LocationResResultDto locationResResultDto : locationResResultDtoList){
            locationResDtoList.add(
                    LocationResDto.builder()
                            .sid(locationResResultDto.getSid())
                            .name(locationResResultDto.getName())
                            .city(locationResResultDto.getCity())
                            .region(locationResResultDto.getRegion())
                            .build()
            );
        }
        return ResponseDto.success(locationResDtoList);
    }
    public ResponseDto<?> searchPostsFromLocations(Long locationId){
        return ResponseDto.success(postsRepository.findAllByLocationId(locationId));
    }
}

package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.exception.ResultNotFoundException;
import com.search.instagramsearching.repository.LocationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocationsService {
    private final LocationsRepository locationsRepository;

    public ResponseDto<?> search(String keyword, Pageable pageable) {
        throw new ResultNotFoundException();
    }
}

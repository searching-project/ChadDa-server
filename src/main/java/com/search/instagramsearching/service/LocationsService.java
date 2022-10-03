package com.search.instagramsearching.service;

import com.search.instagramsearching.repository.LocationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocationsService {
    private final LocationsRepository locationsRepository;
}

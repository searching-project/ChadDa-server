package com.search.instagramsearching.service;

import com.search.instagramsearching.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;
}

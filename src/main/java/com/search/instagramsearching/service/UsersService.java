package com.search.instagramsearching.service;

import com.search.instagramsearching.entity.Users;
import com.search.instagramsearching.exception.ResultNotFoundException;
import com.search.instagramsearching.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public List<Users> naturalSearch(String keyword, Pageable pageable) {
        List<Users> result = usersRepository.ngramNatualSearch(keyword, pageable);
        if (result == null || result.size() == 0) {
            throw new ResultNotFoundException();
        }
        return result;
    }

    @Transactional
    public List<Users> booleanSearch(String keyword, Pageable pageable) {
        List<Users> result = usersRepository.ngramNatualSearch(keyword, pageable);
        if (result == null || result.size() == 0) {
            throw new ResultNotFoundException();
        }
        return result;
    }
}
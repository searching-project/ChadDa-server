package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.response.UserSearchResponseDto;
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
    public List<?> naturalSearch(String keyword, Pageable pageable) {
        String original = keyword;
        keyword = getAnd(keyword);
        List<UserSearchResponseDto> result = usersRepository.ngramNatualSearch(keyword, original, pageable);
        if (result == null || result.size() == 0) {
            throw new ResultNotFoundException();
        }
        return result;
    }

    @Transactional
    public List<?> booleanSearch(String keyword, Pageable pageable) {
        String original = keyword;
        keyword = getAnd(keyword);
        List<UserSearchResponseDto> result = usersRepository.ngramBooleanSearch(keyword, original, pageable);
        if (result == null || result.size() == 0) {
            throw new ResultNotFoundException();
        }
        return result;
    }

    // "검색어1" AND "검색어2"가 모두 포함된 데이터를 조회
    private String getAnd(String str) {
        return "+"+str.replace(" "," +");
    }
}
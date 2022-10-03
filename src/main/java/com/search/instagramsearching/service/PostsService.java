package com.search.instagramsearching.service;

import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.exception.CustomException;
import com.search.instagramsearching.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.search.instagramsearching.exception.ErrorCode.RESULT_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public ResponseDto<?> search(String keyword, Pageable pageable){
        throw new CustomException(RESULT_NOT_FOUND);
    }
}

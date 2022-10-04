package com.search.instagramsearching.service;

import com.search.instagramsearching.entity.Posts;
import com.search.instagramsearching.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public List<Posts> search(String keyword, Pageable pageable){
        List<Posts> posts= postsRepository.search(keyword,pageable);
        return posts;
    }
}

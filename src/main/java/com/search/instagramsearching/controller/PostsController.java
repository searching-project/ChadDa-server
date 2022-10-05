package com.search.instagramsearching.controller;

import com.search.instagramsearching.dto.response.PostResponseDto;
import com.search.instagramsearching.entity.Posts;
import com.search.instagramsearching.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/")
@RestController
public class PostsController {
    private final PostsService postsService;

    @GetMapping("search/post/{keyword}")
    public List<?> search(@PathVariable String keyword, @PageableDefault(page = 0, size = 5) Pageable pageable){
//        return postsService.search(keyword,pageable);
        return postsService.searchView(keyword,pageable);
    }
}
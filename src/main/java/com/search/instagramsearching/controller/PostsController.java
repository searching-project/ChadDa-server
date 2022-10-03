package com.search.instagramsearching.controller;

import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.service.PostsService;
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
public class PostsController {
    private final PostsService postsService;

    @GetMapping("search/{keyword}")
    public ResponseDto<?> search(@PathVariable String keyword, @PageableDefault(page = 0, size = 20) Pageable pageable){
        return ResponseDto.success(postsService.search(keyword,pageable));
    }
}

package com.search.instagramsearching.controller;

import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/search")
@RestController
public class UsersController {
    private final UsersService usersService;

//    @GetMapping("/user/{keyword}")
//    public ResponseDto<?> search(@PathVariable String keyword, @PageableDefault(page = 0, size = 20) Pageable pageable) {
//        return ResponseDto.success(usersService.search(keyword, pageable));
//    }

    @GetMapping("/user/n/{keyword}")
    public ResponseDto<?> naturalSearch(@PathVariable String keyword, @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseDto.success(usersService.naturalSearch(keyword, pageable));
    }

    @GetMapping("/user/b/{keyword}")
    public ResponseDto<?> booleanSearch(@PathVariable String keyword, @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseDto.success(usersService.booleanSearch(keyword, pageable));
    }
}
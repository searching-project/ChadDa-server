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
@RequestMapping("/api")
@RestController
public class UsersController {
    private final UsersService usersService;

    // 유저 검색하기 API
    @GetMapping("/search/user/{keyword}")
    public ResponseDto<?> searchUsers(@PathVariable String keyword, @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseDto.success(usersService.searchUsers(keyword, pageable));
    }

    // 유저 profileName으로 유저의 게시글 조회하기 API
    @GetMapping("/user/{profileName}/posts")
    public ResponseDto<?> getUserPosts(@PathVariable String profileName, @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseDto.success(usersService.getUserPosts(profileName, pageable));
    }
}
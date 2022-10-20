package com.search.instagramsearching.controller;

import com.search.instagramsearching.dto.UserInfoDto;
import com.search.instagramsearching.dto.request.LoginReqDto;
import com.search.instagramsearching.dto.request.SignupRequestDto;
import com.search.instagramsearching.dto.response.MessageDto;
import com.search.instagramsearching.dto.response.ResponseDto;
import com.search.instagramsearching.security.user.UserDetailsImpl;
import com.search.instagramsearching.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final UsersService usersService;
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails!=null){
            model.addAttribute("username", userDetails.getUsername());
        }
        return "index";
    }
    @GetMapping("/user-posts")
    public String userForm() {
        return "user-posts.html";
    }

    // 회원 로그인 페이지
    @GetMapping("/user/loginView")
    public String login() {
        return "login";
    }


    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto) {
        try {
            usersService.registerUser(requestDto);
        }catch (Exception e){
            return "redirect:/user/signup?error";
        }
        return "redirect:/user/loginView";
    }


}

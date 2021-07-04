package com.sparta.springcore.controller;

import com.sparta.springcore.security.UserDetailsImpl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {     // @AuthenticationPrincipal 통해서 로그인 된 사람의 정보를 자동으로 넘겨줌
        model.addAttribute("username", userDetails.getUsername());                  // thymeleaf를 통해서 username 전달되어 줌
        return "index";
    }

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")  // role이 관리자인 사람에게만 접근 가능하도록 설치(WebSecurityConfig의 @EnableGlobalMethodSecurity 덕분에 가능)
    public String admin(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {    // 관리자 권한을 가지고 로그인
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("admin", true);                              // 관리자 권한을 가진 사람에게만 관리자 페이지 볼 수 있도록 값 설정
        return "inndex";
    }
}
package com.study.security.api.controller;

import com.study.security.api.controller.request.SignUpRequest;
import com.study.security.api.service.CustomMemberDetailsService;
import com.study.security.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("userDto", new SignUpRequest());
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute("signUpRequest") SignUpRequest request) {
        memberService.signUp(request.toService());
        return "redirect:/login";
    }

}

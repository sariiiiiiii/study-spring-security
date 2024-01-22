package com.study.security.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {

    @GetMapping("/system")
    public String system() {
        return "system";
    }

    @GetMapping("/system/create")
    public String create() {
        return "systemCreate";
    }

    @GetMapping("/system/delete")
    public String delete() {
        return "systemDelete";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

}

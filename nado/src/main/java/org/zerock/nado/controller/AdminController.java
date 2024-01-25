package org.zerock.nado.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/auth")
public class AdminController {

    @GetMapping("/admin")
    public String adminP(){
        return "admin Controller";
    }
}

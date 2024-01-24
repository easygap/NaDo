package org.zerock.nado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/travel")
public class DangerousController {

    @GetMapping("/danger")
    public String showDangerPage(Model model) {
        model.addAttribute("message", "Travel-Information Page!");
        return "danger/dangerous";
    }
}

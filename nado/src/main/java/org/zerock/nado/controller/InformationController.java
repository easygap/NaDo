package org.zerock.nado.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.nado.API.ApiInfor;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/travel")
public class InformationController {

    @GetMapping("/infor")
    public String InformationPage(Model model) {
        return "travel/inforList";
    }

    @PostMapping("/infor")
    public String InforParameter(String searchInfor, Model model) throws IOException {
        log.info("Information post------------------");

        String countryInfor = ApiInfor.getEmbassyList(searchInfor);
        String countryName = searchInfor;

        System.out.println("countryName은??!?!?! 바로바로?!?!?! : " + searchInfor);
        System.out.println("countryInfor는??!?!?! 바로바로?!?!?! : " + countryInfor);

        model.addAttribute("countryInfor", countryInfor);
        model.addAttribute("countryName", countryName);

        return "travel/inforList";
    }
}

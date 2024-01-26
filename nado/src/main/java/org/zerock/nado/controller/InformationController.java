package org.zerock.nado.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

        System.out.println("countryName은??!?!?! 바로바로?!?!?! : " + searchInfor);

        model.addAttribute("countryInfor", countryInfor);
        model.addAttribute("countryName", searchInfor);

        return "travel/inforList";
    }

    @GetMapping("/infor/api")
    @ResponseBody
    public String getInformationApi(String searchInfor) throws IOException {
        log.info("API Information request-------------------");

        // API에서 정보를 가져오는 메서드 호출 (예: ApiInfor.getEmbassyList)
        String countryInfor = ApiInfor.getEmbassyList(searchInfor);

        log.info("Received API Information for: {}", searchInfor);

        return countryInfor;
    }
}

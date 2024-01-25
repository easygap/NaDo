package org.zerock.nado.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.nado.API.ApiDanger;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/travel")
public class DangerousController {

    @GetMapping("/danger")
    public String showDangerPage(Model model) {
        List<String> dangerList = new ArrayList<>();

        log.info("dangerous..........");

        try {
            dangerList = ApiDanger.getDangerList();
            log.info("dangerousList : " + dangerList);

            // 파싱한 데이터를 Model에 담아 View로 전달
            model.addAttribute("dangerList", dangerList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while fetching dangerous countries.");
            log.info("An error occurred while fetching dangerous countries.");
        }
        return "danger/dangerous";
    }
}

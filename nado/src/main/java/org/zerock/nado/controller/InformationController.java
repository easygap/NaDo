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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/travel")
public class InformationController {

    @GetMapping("/infor")
    public String InformationPage(Model model) {
        return "travel/information";
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
        log.info("API 정보 요청-------------------");

        // API에서 정보를 가져오는 메서드 호출 (예: ApiInfor.getEmbassyList)
        String countryInfor = ApiInfor.getEmbassyList(searchInfor);

        log.info("받아온 API 정보: {}", countryInfor);

        // 받아온 XML 데이터를 Jsoup을 사용하여 파싱
        Document document = Jsoup.parse(countryInfor);

        // 기본 정보 추출
        String basicInfo = document.select("item basic").text();
        if (basicInfo.equals("")) {
            String result = "<br><h2>조회할수없는 나라입니다.</h2><br>" +
                             "<h3>나라명이 틀렸는지 지역명을 적으셨는지 확인해주세요.</h3><br>" +
                             "<h5>1.나라명으로만 조회가 가능합니다.</h5><br>" +
                             "<h5>2.일부국가는 지원되지않습니다.</h5><br>";
            return result;
        } else {
            // <br> 태그를 기준으로 문자열을 나누어 리스트에 담기
            String[] infoArray = basicInfo.split("<div>");

            StringBuilder result = new StringBuilder();

            // 나눈 정보를 출력 또는 활용하기
            for (String info : infoArray) {
                String trimmedInfo = info.trim();
                if (!trimmedInfo.isEmpty()) {
                    result.append(trimmedInfo);
                }
            }
            return result.toString();
        }
    }
}

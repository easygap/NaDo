package org.zerock.nado.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.nado.API.ApiExplorer;
import org.zerock.nado.dto.EmbassyInfoDTO;
import org.zerock.nado.dto.SptravelWarningListDTO;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/nado")
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 Annotation
public class ViewCountrySpecificInformationController {

    @GetMapping("/infor/api")
    @ResponseBody
    public String getInformationApi(String searchInfor) throws IOException {
        String Download;
        // API에서 정보를 가져오는 메서드 호출 (예: ApiInfor.getEmbassyList)
        List<EmbassyInfoDTO> embassyList = ApiExplorer.getEmbassyList(searchInfor);
        String countryInfor = ApiExplorer.getSecurityEnvironment(searchInfor);
        List<SptravelWarningListDTO> SptravelWarningMap = ApiExplorer.getSptravelWarningMap(searchInfor);

        StringBuilder embassyListHtml;
        String countryInfor2;

        
        //여행경보 조건문
        if (countryInfor.equals("여행 경보 null\n") || countryInfor.equals("")) {
            countryInfor2 = "<h1 style=\"color: red;\">위험경보를 지원하지않는 국가입니다.<h1>";
        }else {
            countryInfor2 = "<h1>" + countryInfor + "</h1>";
        }

        // 대사관 조건문
        if (embassyList.isEmpty()) {
            embassyListHtml = new StringBuilder("<h3 style=\"color: red;\">※해당국가에는 대한민국 대사관이 존재하지 않습니다.※</h3>");
        }else {
            // embassyList를 HTML 문자열로 변환
            embassyListHtml = new StringBuilder("<ul>");
            for (EmbassyInfoDTO embassy : embassyList) {
                embassyListHtml.append("<li>").append("이름 : " + embassy.getName()).append("</li>");
                embassyListHtml.append("<li>").append("주소 : " + embassy.getAddress()).append("</li>");
                embassyListHtml.append("<li>").append("번호 : " + embassy.getPhoneNumber()).append("</li>");
                embassyListHtml.append("<li>").append("긴급번호 : " + embassy.getUrgencyNumber()).append("</li>");
                embassyListHtml.append("<li>").append("위도 : " + embassy.getLat()).append("</li>");
                embassyListHtml.append("<li>").append("경도 : " + embassy.getLng()).append("</li>");
                embassyListHtml.append("<br>");
                embassyListHtml.append("<br>");
            }
            embassyListHtml.append("</ul>");
        }
        // 위험지도 조건문
        if (SptravelWarningMap.isEmpty()) {
            Download = "<h2 style=\"color: red;\">※조회할수없는 국가입니다.※</h2><br>" +
                    "<h3>국가명이 틀렸는지 지역명을 적으셨는지 확인해주세요.</h3><br>" +
                    "<h5>1.국가명으로만 조회가 가능합니다.</h5>" +
                    "<h5>2.일부국가는 지원되지않습니다.</h5>" +
                    "<h5>3.국가명을 한글로적어야 정상작동이 가능합니다.";
            countryInfor2 = "";
            embassyListHtml = new StringBuilder("");
        }else {
            // SptravelWarningListDTO 객체의 getUrl() 메서드를 사용하여 URL을 가져와서 링크 생성
            Download = "<h2><a href=\"" + SptravelWarningMap.get(0).getUrl() + "\">지역별 위험구역 지도 다운로드</a></h2>";
        }

        System.out.println("embassyList" + embassyList);
        System.out.println("embassyListHtml.toString()" + embassyListHtml.toString());
        return countryInfor2 + "<br>" + Download + "<br>" + embassyListHtml.toString();
    }
}
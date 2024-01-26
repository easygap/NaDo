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
    @GetMapping("/ViewCountry-SpecificInformation")
    public String ViewCountrySpecificInformation(@RequestParam("gno") Long gno, @RequestParam("title") String title, Model model) {
        // 게시물 정보를 로깅
        System.out.println("게시물 번호 : " + gno);
        System.out.println("게시물 제목 : " + title);

        if (title != null) {
            try {
                // API 호출 및 응답 가져오기 (리스트로 변경된 부분)
                List<EmbassyInfoDTO> embassyList = ApiExplorer.getEmbassyList(title);
                String SecurityEnvironment = ApiExplorer.getSecurityEnvironment(title);
                List<SptravelWarningListDTO> SptravelWarningMap = ApiExplorer.getSptravelWarningMap(title);

                if (!embassyList.isEmpty()) {
                    // 첫 번째 대사관 정보만 사용하도록 예시로 설정
                    EmbassyInfoDTO embassyInfoDTO = embassyList.get(0);

                    // 모델에 대사관 정보 추가
                    model.addAttribute("embassyList", embassyList);
                    model.addAttribute("SecurityEnvironmentDTO", SecurityEnvironment);
                    model.addAttribute("SptravelWarningMap", SptravelWarningMap.get(0));
                    System.out.println("1, embassyList : " + embassyList);
                    System.out.println("2, SecurityEnvironment : " + SecurityEnvironment);
                    System.out.println("3, URL : " + SptravelWarningMap.get(0));
                } else {
                    // 대사관 정보가 없을 경우 처리
                    System.out.println("대사관 정보가 없습니다.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                // 예외 처리는 필요에 따라 수정하세요.
                System.out.println("API 호출 중 예외 발생");
            }
        } else {
            // ISO 코드를 찾을 수 없는 경우 처리
            System.out.println("ISO 코드를 찾을 수 없습니다.");
        }

        // 모델에 "title" 속성 추가
        model.addAttribute("title", title);
        model.addAttribute("gno", gno);

        // 뷰의 이름을 반환 (여기서는 "ViewCountry-SpecificInformation.html"이라고 가정)
        return "/nado/ViewCountry-SpecificInformation";
    }

    @GetMapping("/infor/api")
    @ResponseBody
    public String getInformationApi(String searchInfor) throws IOException {
        log.info("API Information request-------------------");

        // API에서 정보를 가져오는 메서드 호출 (예: ApiInfor.getEmbassyList)
        List<EmbassyInfoDTO> embassyList = ApiExplorer.getEmbassyList(searchInfor);
        String countryInfor = ApiExplorer.getSecurityEnvironment(searchInfor);
        List<SptravelWarningListDTO> SptravelWarningMap = ApiExplorer.getSptravelWarningMap(searchInfor);

        log.info("Received API Information for: {}", searchInfor);

        String countryInfor2 = "<h1>" + countryInfor + "</h1>";
        // SptravelWarningListDTO 객체의 getUrl() 메서드를 사용하여 URL을 가져와서 링크 생성
        String Download = "<h2><a href=\"" + SptravelWarningMap.get(0).getUrl() + "\">지역별 위험구역 지도 다운로드</a></h2>";
        // embassyList를 HTML 문자열로 변환
        StringBuilder embassyListHtml = new StringBuilder("<ul>");
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

        return countryInfor2 + "<br>" + Download + "<br>" + embassyListHtml.toString();
    }
}
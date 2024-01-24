package org.zerock.nado.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.nado.API.ApiExplorer;
import org.zerock.nado.dto.NadoDTO;
import org.zerock.nado.dto.PageRequestDTO;
import org.zerock.nado.service.NadoService;

//API
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/nado")
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 Annotation
public class NadoController {
    private final NadoService service; // final로 선언

    @GetMapping("/")
    public String index() {
        return "redirect:/nado/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list......................" + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(NadoDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);

        // 새로 추가된 엔티티의 번호
        Long gno = service.register(dto);

        System.out.println("gno의 값은 : " + gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/nado/list";
    }

    // @GetMapping("/read")
    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("read ===================================== gno: " + gno);

        NadoDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {
        log.info("gno: " + gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/nado/list";
    }

    @PostMapping("/modify")
    public String modify(NadoDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        log.info("post modify...................................................................");
        log.info("dto: " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/nado/read";
    }

    @GetMapping("/Community")
    public void Community() {

    }

    // 국가 이름과 ISO 코드 매핑을 위한 데이터 구조
    private static final Map<String, String> countryIsoCodeMap = new HashMap<>();

    static {
        countryIsoCodeMap.put("가나", "GH");
        countryIsoCodeMap.put("미국", "US");
        countryIsoCodeMap.put("일본", "JP");
        // 다른 국가들에 대한 매핑 추가
    }

    @GetMapping("/ViewCountry-SpecificInformation")
    public String ViewCountrySpecificInformation(@RequestParam("gno") Long gno, @RequestParam("title") String title, Model model) {
        // 게시물 정보를 로깅
        System.out.println("게시물 번호 : " + gno);
        System.out.println("게시물 제목 : " + title);

        // 국가 이름으로부터 ISO 코드를 가져오기
        String isoCode = countryIsoCodeMap.get(title);

        if (isoCode != null) {
            try {
                // API 호출 및 응답 가져오기
                String apiResponse = ApiExplorer.getEmbassyList(title, isoCode);

                // API 응답 파싱 및 필요한 정보 추출
                String extractedInfo = ApiExplorer.parseApiResponse(apiResponse);

                // 추출된 정보를 쉼표로 분리하여 embassyName, embassyAddress, embassyPhoneNumber에 할당
                String[] infoArray = extractedInfo.split("/ ");
                String embassyName = infoArray[0];
                String embassyAddress = infoArray[1];
                String embassyPhoneNumber = infoArray[2];

                // 모델에 추출된 정보 추가
                model.addAttribute("embassyName", embassyName);
                model.addAttribute("embassyAddress", embassyAddress);
                model.addAttribute("embassyPhoneNumber", embassyPhoneNumber);
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
}
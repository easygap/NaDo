package org.zerock.nado.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.nado.API.ApiExplorer;
import org.zerock.nado.API.EmbassyInfo;
import org.zerock.nado.API.SecurityEnvironment;
import org.zerock.nado.API.SptravelWarningList;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@ResponseBody
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

    @GetMapping("/ViewCountry-SpecificInformation")
    public String ViewCountrySpecificInformation(@RequestParam("gno") Long gno, @RequestParam("title") String title, Model model) {
        // 게시물 정보를 로깅s
        System.out.println("게시물 번호 : " + gno);
        System.out.println("게시물 제목 : " + title);

        if (title != null) {
            try {
                // API 호출 및 응답 가져오기 (리스트로 변경된 부분)
                List<EmbassyInfo> embassyList = ApiExplorer.getEmbassyList(title);
                String SecurityEnvironment = ApiExplorer.getSecurityEnvironment(title);
                List<SptravelWarningList> SptravelWarningMap = ApiExplorer.getSptravelWarningMap(title);

                if (!embassyList.isEmpty()) {
                    // 첫 번째 대사관 정보만 사용하도록 예시로 설정
                    EmbassyInfo embassyInfo = embassyList.get(0);

                    // 모델에 대사관 정보 추가
                    model.addAttribute("embassyList", embassyList);
                    model.addAttribute("SecurityEnvironment", SecurityEnvironment);
                    model.addAttribute("SptravelWarningMap", SptravelWarningMap.get(0));
                    System.out.println("URL : " + SptravelWarningMap.get(0));
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
}
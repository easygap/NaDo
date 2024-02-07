package org.zerock.nado.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.nado.dto.CommentsDTO;
import org.zerock.nado.dto.NadoDTO;
import org.zerock.nado.dto.PageRequestDTO;
import org.zerock.nado.service.CommentsService;
import org.zerock.nado.service.NadoService;

import java.util.Map;

@Controller
@RequestMapping("/nado")
@Log4j2
@RequiredArgsConstructor
public class NadoController {

    private final NadoService service;
    private final CommentsService comService;

    @GetMapping("/")
    public String index() {
        return "redirect:/nado/list";
    }

    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list.................." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    //---------------------------------------------------------------------------------------
    @GetMapping("/register")
    public void register () {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(NadoDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);

        //새로 추가된 엔티티의 번호
        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/nado/list";
    }
    @GetMapping({"/read","/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model, CommentsDTO commentsDTO) {
        log.info("gno: " + gno);
        NadoDTO dto = service.read(gno);
        model.addAttribute("dto", dto);

        여기에 댓글 정보 불러오려고 함.
    }

    @PostMapping("/read")
    public String comments(CommentsDTO dto, NadoDTO nadoDTO, RedirectAttributes redirectAttributes){
        System.out.println("dto :    " + dto);

        Long cno = comService.CommRegister(dto);
        redirectAttributes.addAttribute("gno", nadoDTO.getGno());
        redirectAttributes.addAttribute("title", nadoDTO.getTitle());
        redirectAttributes.addAttribute("cno", dto.getCno());

        return "redirect:/nado/read";
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {
        log.info("gno: " + gno);
        service.remove(gno);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/nado/list";
    }

    @PostMapping("/modify")
    public String modify (NadoDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        System.out.println("확인용 : " + dto);
        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/nado/read";
    }

    @PostMapping("/checkPassword")
    @ResponseBody
    public String checkPassword(@RequestBody Map<String, String> requestBody) {

        String password = requestBody.get("password"); // 입력값
        String correctPassword = service.getPasswordByGno(Long.valueOf(requestBody.get("gno"))); // DB에서 게시물번호로 조회한 게시물 비밀번호

        System.out.println("Password : " + password);
        System.out.println("correctPassword : " + correctPassword);
        if (password.equals(correctPassword)) {
            return "success";
        } else {
            return "failure";
        }
    }
}

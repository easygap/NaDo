package org.zerock.nado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.nado.dto.MemberDTO;
import org.zerock.nado.service.MemberService;

@Controller /** 해당 클래스는 컨트롤러임을 알림, Bean으로 등록 */
@RequiredArgsConstructor /** 추후 의존관계 관련하여 필요 */
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String createMember() {
        return "auth/join";
    }

    @PostMapping("/joinAccess")
    public String createMember(@ModelAttribute MemberDTO memberDTO){

        System.out.println(memberDTO.getName());
        memberService.createMember(memberDTO);

        return "ok";
    }
}
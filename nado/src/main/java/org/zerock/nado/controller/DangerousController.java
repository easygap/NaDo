package org.zerock.nado.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.nado.service.NadoService;

@Controller
@RequestMapping("/nado")
@Log4j2
@RequiredArgsConstructor
public class DangerousController {
    private final NadoService service;

    @GetMapping("/dangerous")
    public void dangerousList(){
        log.info("dangerous.......");
    }
}

package org.zerock.nado.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.nado.dto.NadoDTO;
import org.zerock.nado.dto.PageRequestDTO;
import org.zerock.nado.dto.PageResultDTO;
import org.zerock.nado.entity.Nado;

@SpringBootTest
public class NadoServiceTests {
    @Autowired
    private NadoService service;

    @Test
    public void testRegister(){
        NadoDTO nadoDTO = NadoDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();

        System.out.println(service.register(nadoDTO));
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).
                size(10).build();
        PageResultDTO<NadoDTO, Nado> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("------------------------------------");
        for(NadoDTO nadoDTO : resultDTO.getDtoList()){
            System.out.println(nadoDTO);
        }

        System.out.println("------------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    @Test
    public void testSearch(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")  // 검색 조건 t, c, w, tc, tcw ..
                .keyword("모발")  // 검색 키워드
                .build();

        PageResultDTO<NadoDTO, Nado> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("---------------------------------------");
        for (NadoDTO nadoDTO : resultDTO.getDtoList()) {
            System.out.println(nadoDTO);
        }

        System.out.println("=======================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}

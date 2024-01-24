package org.zerock.nado.service;

import org.zerock.nado.API.ApiExplorer;
import org.zerock.nado.dto.NadoDTO;
import org.zerock.nado.dto.PageRequestDTO;
import org.zerock.nado.dto.PageResultDTO;
import org.zerock.nado.entity.Nado;

import java.io.IOException;

public interface NadoService {
    Long register(NadoDTO dto);

    NadoDTO read(Long gno);

    void remove(Long gno);

    void modify(NadoDTO dto);

    PageResultDTO<NadoDTO, Nado> getList(PageRequestDTO requestDTO);

    default Nado dtoToEntity(NadoDTO dto) {
        Nado entity = Nado.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default NadoDTO entityToDto(Nado entity){
        System.out.println("entity.getGno() : " + entity.getGno());

        NadoDTO dto = NadoDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        System.out.println("dto.getGno() : " + dto.getGno());
        return dto;
    }
}
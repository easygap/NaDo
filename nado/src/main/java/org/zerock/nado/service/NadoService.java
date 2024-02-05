package org.zerock.nado.service;

import org.zerock.nado.entity.Nado;
import org.zerock.nado.dto.NadoDTO;
import org.zerock.nado.dto.PageRequestDTO;
import org.zerock.nado.dto.PageResultDTO;

public interface NadoService {
    Long register(NadoDTO dto);

    NadoDTO read(Long gno);

    PageResultDTO<NadoDTO, Nado> getList(PageRequestDTO requestDTO);

    void remove(Long gno);
    void modify(NadoDTO dto);

    default Nado dtoToEntity(NadoDTO dto) {
        Nado entity = Nado.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }

    default NadoDTO entityToDto (Nado entity) {
        NadoDTO dto = NadoDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }
}

package org.zerock.nado.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.nado.API.ApiExplorer;
import org.zerock.nado.dto.NadoDTO;
import org.zerock.nado.dto.PageRequestDTO;
import org.zerock.nado.dto.PageResultDTO;
import org.zerock.nado.entity.Nado;
import org.zerock.nado.entity.QNado;
import org.zerock.nado.repository.NadoRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class NadoServiceImpl implements NadoService {
    private final NadoRepository repository; // 반드시 final로 선언

    @Override
    public Long register(NadoDTO dto) {
        log.info("DTO------------------------");
        log.info(dto);

        Nado entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<NadoDTO, Nado> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 처리

        Page<Nado> result = repository.findAll(booleanBuilder, pageable); // Querydsl 사용

        Function<Nado, NadoDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public Nado dtoToEntity(NadoDTO dto) {
        return NadoService.super.dtoToEntity(dto);
    }

    @Override
    public NadoDTO read(Long gno) {
        Optional<Nado> result = repository.findById(gno);

        System.out.println("ServiceImple : read : " + gno);
        System.out.println("result.isPresent() : " + result.isPresent());

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(NadoDTO dto) {
        // 업데이트 하는 항목은 '제목', '내용'

        Optional<Nado> result = repository.findById(dto.getGno());

        if (result.isPresent()) {
            Nado entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) { // Querydsl 처리
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QNado qNado = QNado.nado;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qNado.gno.gt(0L); // gno > 0 조건만 생성

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) { // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        // 검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qNado.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qNado.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qNado.writer.contains(keyword));
        }

        // 모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
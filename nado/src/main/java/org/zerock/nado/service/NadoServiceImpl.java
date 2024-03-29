package org.zerock.nado.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.nado.entity.Nado;
import org.zerock.nado.entity.QNado;
import org.zerock.nado.dto.NadoDTO;
import org.zerock.nado.dto.PageRequestDTO;
import org.zerock.nado.dto.PageResultDTO;
import org.zerock.nado.repository.NadoRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class NadoServiceImpl implements NadoService {

    private final NadoRepository repository;

    @Override
    public Long register(NadoDTO dto) {
        log.info("DTO--------------------------");
        log.info(dto);

        Nado entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }
    public PageResultDTO<NadoDTO, Nado> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<Nado> result = repository.findAll(booleanBuilder, pageable);

        Function<Nado, NadoDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    public Nado dtotoEntity(NadoDTO dto) {
        return NadoService.super.dtoToEntity(dto);
    }

    public NadoDTO read(Long gno) {
        Optional<Nado> result = repository.findById(gno);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    public void modify (NadoDTO dto) {
        System.out.println(dto.getGno());
        Optional<Nado> result = repository.findById(dto.getGno());


        if (result.isPresent()) {
            Nado entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QNado qNado = QNado.nado;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qNado.gno.gt(0L);

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

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

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

    public String getPasswordByGno(Long gno) {
        System.out.println("gno : " + gno);
        System.out.println("repository.findById(gno) : " + repository.findById(gno));
        Optional<Nado> result = repository.findById(gno);

        if (result.isPresent()) {
            Nado entity = result.get();
            return entity.getPassword(); // 예시로 getPassword() 메서드를 사용하여 게시물의 비밀번호를 가져옴
        } else {
            return null;
        }
    }
}
package org.zerock.nado.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.nado.dto.CommentsDTO;
import org.zerock.nado.entity.Comment;
import org.zerock.nado.repository.CommentsRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService{

    private final CommentsRepository repository;

    @Override
    public Long CommRegister(CommentsDTO dto){
        log.info("CommentsDTO ------------ ");

        Comment entity = toComments(dto);

        System.out.println("CommentsServiceImpl : " + entity);

        repository.save(entity);

        return entity.getCno();
    }
}

package org.zerock.nado.service;

import org.zerock.nado.dto.CommentsDTO;
import org.zerock.nado.entity.Comment;

public interface CommentsService {

    default Comment toComments(CommentsDTO dto){
        Comment comments = Comment.builder()
                .cno(dto.getCno())
                .gno(dto.getGno())
                .comContents(dto.getComContents())
                .comWriter(dto.getComWriter())
                .comPWD(dto.getComPWD())
                .build();

        return comments;
    }

    Long CommRegister(CommentsDTO dto);
}

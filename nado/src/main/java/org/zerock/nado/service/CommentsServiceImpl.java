package org.zerock.nado.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.nado.dto.CommentsDTO;
import org.zerock.nado.entity.Comment;
import org.zerock.nado.entity.Nado;
import org.zerock.nado.repository.CommentsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<CommentsDTO> getCommentsByGno(Long gno) {
        List<Comment> comments = repository.findByGno(gno);
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Comment를 CommentsDTO로 변환하는 메서드 정의
    private CommentsDTO convertToDTO(Comment comment) {
        return CommentsDTO.builder()
                .cno(comment.getCno())
                .gno(comment.getGno())
                .comContents(comment.getComContents())
                .comWriter(comment.getComWriter())
                .comPWD(comment.getComPWD())
                .regDate(comment.getRegDate())
                .modDate(comment.getModDate())
                .build();
    }

    public String getcomPasswordByCno(Long cno) {
        System.out.println("gno : " + cno);
        System.out.println("repository.findById(gno) : " + repository.findById(cno));
        Optional<Comment> result = repository.findById(cno);

        if (result.isPresent()) {
            Comment entity = result.get();
            return entity.getComPWD(); // 예시로 getPassword() 메서드를 사용하여 게시물의 비밀번호를 가져옴
        } else {
            return null;
        }
    }

    public void remove(Long cno) {
        repository.deleteById(cno);
    }
}

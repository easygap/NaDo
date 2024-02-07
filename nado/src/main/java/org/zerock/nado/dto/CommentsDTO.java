package org.zerock.nado.dto;

import lombok.*;
import org.zerock.nado.entity.Comment;
import org.zerock.nado.entity.Nado;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentsDTO {
    private Long cno;
    private Long gno;
    private String comContents, comWriter, comPWD;
    private LocalDateTime regDate, modDate;
}

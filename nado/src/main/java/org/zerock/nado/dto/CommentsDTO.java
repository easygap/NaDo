package org.zerock.nado.dto;

import lombok.*;
import org.zerock.nado.entity.Comment;
import org.zerock.nado.entity.Nado;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentsDTO {
    private Long cno;
    private Nado gno;
    private String comContents, comWriter, comPWD;
}

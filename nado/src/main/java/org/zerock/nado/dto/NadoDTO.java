package org.zerock.nado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.nado.entity.Nado;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NadoDTO {
    private Long gno;
    private Nado comgno;
    private String title;
    private String content;
    private String writer;
    private String password;
    private LocalDateTime regDate, modDate;
}

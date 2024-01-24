package org.zerock.nado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data /** Getter & Setter */
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String id;
    private String pass;
    private String name;
    private String tel;
}

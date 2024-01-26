package org.zerock.nado.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data               /** Getter Setter */
@Builder            /** DTO -> Entity화 */
@AllArgsConstructor /** 모든 컬럼 생성자 생성 */
@NoArgsConstructor  /** 기본 생성자 */
@Table(name = "member")
public class Member {

    @Id /** PK */
    @Column(length = 20,unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String pass;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 14, nullable = false)
    private String tel;

    @Column(length = 14, nullable = false)
    private String role;

}

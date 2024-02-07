package org.zerock.nado.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    @ManyToOne
    @JoinColumn(name="commGno")
    private Nado gno;

    @Column(name = "comContents", nullable = false)
    private String comContents;

    @Column(name = "comWriter", nullable = false)
    private String comWriter;

    @Column(name = "comPWD", nullable = false)
    private String comPWD;
}

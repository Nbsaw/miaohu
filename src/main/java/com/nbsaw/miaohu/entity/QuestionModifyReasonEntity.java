package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "question_modify_reason")
@Data
public class QuestionModifyReasonEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private String reason;
}

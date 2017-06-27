package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * Created by nbsaw on 2017/5/11.
 */
@Entity
@Table(name = "question_modify_reason")
@Data
public class QuestionModifyReasonEntity {
    @Id
    @GeneratedValue
    private Long id;

    // 问题的id
    @Column(nullable = false)
    private Long questionId;

    // 修改理由
    @Column(nullable = false)
    private String reason;
}

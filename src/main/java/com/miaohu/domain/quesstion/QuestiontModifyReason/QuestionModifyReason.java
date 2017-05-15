package com.miaohu.domain.quesstion.QuestiontModifyReason;

import javax.persistence.*;

/**
 * Created by nbsaw on 2017/5/11.
 */
@Entity
@Table(name = "question_modify_reason")
public class QuestionModifyReason {
    @Id
    @GeneratedValue
    private Long id;

    // 问题id
    @Column(nullable = false)
    private Long questionID;

    // 修改理由
    @Column(nullable = false)
    private String reason;

}

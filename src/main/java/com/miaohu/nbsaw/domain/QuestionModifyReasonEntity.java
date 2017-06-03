package com.miaohu.nbsaw.domain;

import javax.persistence.*;

/**
 * Created by nbsaw on 2017/5/11.
 */
@Entity
@Table(name = "question_modify_reason")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

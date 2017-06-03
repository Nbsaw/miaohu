package com.miaohu.nbsaw.domain;

import javax.persistence.*;

/**
 * Created by nbsaw on 2017/5/17.
 */
@Entity
@Table(name = "answer_vote_map")
public class AnswerVoteMapEntity {
    @Id
    @GeneratedValue
    private Long id;

    // 回答的id
    private Long answerId;

    // 问题的id
    private Long questionId;

    // 用户的id
    private String uid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}

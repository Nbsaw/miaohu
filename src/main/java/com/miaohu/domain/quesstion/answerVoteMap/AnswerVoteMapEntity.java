package com.miaohu.domain.quesstion.answerVoteMap;

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

    // 问题的id
    private Long questionId;

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
}

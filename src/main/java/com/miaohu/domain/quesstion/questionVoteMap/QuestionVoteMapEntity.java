package com.miaohu.domain.quesstion.questionVoteMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by nbsaw on 2017/5/21.
 */
@Entity
@Table(name = "question_vote_map")
public class QuestionVoteMapEntity {
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

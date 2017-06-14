package com.nbsaw.miaohu.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by nbsaw on 2017/5/17.
 */
@Entity
@Table(name = "answer_vote_map")
@Data

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
}

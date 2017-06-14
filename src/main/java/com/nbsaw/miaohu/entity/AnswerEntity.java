package com.nbsaw.miaohu.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Nbsaw on 17-5-3.
 */
@Entity
@Table(name = "answer")
@Data
public class AnswerEntity {
    @Id
    @GeneratedValue
    private long id;

    // 问题的Id
    @Column(nullable = false)
    private Long questionId;

    // 用户id
    @Column(nullable = false)
    private String uid;

    // 问题内容
    @Lob
    @Column(length = 1000000,nullable = false)
    private String  content;

    // 点赞次数
    @Column(nullable = false)
    private long vote = 0L;

    // 删除状态
    @Column(nullable = false)
    private boolean deleted = false;

    // 匿名状态
    private boolean anonymous = false;
}

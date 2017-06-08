package com.miaohu.nbsaw.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fz on 17-3-31.
 */
@Entity
@Table(name = "question")
@Data
public class QuestionEntity {
    // 问题id
    @Id
    @GeneratedValue
    private Long id;

    // 用户id
    @Column(nullable = false)
    private String uid;

    // 问题标题
    @Column(nullable = false,unique = true)
    private String title;

    // 问题内容
    @Lob
    @Column(length = 1000000)
    private String  content;

    // 发表时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    // 匿名
    private boolean anonymous;
}

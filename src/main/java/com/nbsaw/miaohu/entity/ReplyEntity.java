package com.nbsaw.miaohu.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reply")
@Data
public class ReplyEntity {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    Long articleId;

    @Column(nullable = false)
    String uid;

    @Lob
    @Column(length = 1000000,nullable = false)
    String  content;

    @Column(nullable = false)
    long vote = 0L;

    @Column(nullable = false)
    boolean deleted = false;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();

    // 是否通过审核
    boolean pass;
}

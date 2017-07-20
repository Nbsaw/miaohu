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
    private Long id;

    @Column(nullable = false)
    private Long articleId;

    @Column(nullable = false)
    private String uid;

    @Lob
    @Column(length = 1000000,nullable = false)
    private String  content;

    @Column(nullable = false)
    private long vote = 0L;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();
}

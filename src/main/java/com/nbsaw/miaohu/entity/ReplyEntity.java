package com.nbsaw.miaohu.entity;

import lombok.Data;
import org.hibernate.annotations.Formula;

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
    String content;

    @Formula("(select count(*) from reply_vote o where o.reply_id = id)")
    long vote;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();

    // 是否通过审核
    boolean pass;
}

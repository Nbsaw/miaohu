package com.nbsaw.miaohu.model;

import lombok.Data;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Reply implements Serializable {
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

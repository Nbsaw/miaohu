package com.nbsaw.miaohu.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class ArticleVote implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    Long articleId;

    String uid;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();
}

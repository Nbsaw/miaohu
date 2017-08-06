package com.nbsaw.miaohu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "article_vote")
@Data
public class ArticleVoteEntity implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    Long articleId;

    String uid;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();
}

package com.nbsaw.miaohu.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reply_vote_map")
@Data
public class ReplyVoteMapEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long articleId;

    private String uid;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();
}

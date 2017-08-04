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
    Long id;

    Long articleId;

    String uid;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();
}

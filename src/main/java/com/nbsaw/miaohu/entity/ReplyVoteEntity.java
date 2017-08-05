package com.nbsaw.miaohu.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reply_vote")
@Data
public class ReplyVoteEntity {
    @Id
    @GeneratedValue
    Long id;

    Long replyId;

    String uid;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();
}

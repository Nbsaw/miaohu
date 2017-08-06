package com.nbsaw.miaohu.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reply_vote")
@Data
public class ReplyVoteEntity implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    Long replyId;

    String uid;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();
}

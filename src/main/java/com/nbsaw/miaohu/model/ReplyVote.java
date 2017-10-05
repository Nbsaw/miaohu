package com.nbsaw.miaohu.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class ReplyVote implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    Long replyId;

    String uid;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();
}

package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "answer_vote_map")
@Data
public class AnswerVoteMapEntity implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    Long answerId;

    Long questionId;

    String uid;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();
}

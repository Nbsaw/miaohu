package com.nbsaw.miaohu.model;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class AnswerVoteMap implements Serializable {
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

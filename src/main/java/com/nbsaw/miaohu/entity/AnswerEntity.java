package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "answer")
@Data
public class AnswerEntity {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    Long questionId;

    @Column(nullable = false)
    String uid;

    @Lob
    @Column(length = 1000000,nullable = false)
    String  content;

    @Column(nullable = false)
    long vote = 0L;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();

    @Column(nullable = false)
    boolean deleted = false;

    boolean anonymous = false;
}

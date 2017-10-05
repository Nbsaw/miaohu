package com.nbsaw.miaohu.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Answer implements Serializable {
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

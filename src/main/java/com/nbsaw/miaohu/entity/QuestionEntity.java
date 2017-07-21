package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "question")
@Data
public class QuestionEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false,unique = true)
    private String title;

    @Lob
    @Column(length = 1000000)
    private String content;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    private boolean anonymous;
}

package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "answer")
@Data
public class AnswerEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private String uid;

    @Lob
    @Column(length = 1000000,nullable = false)
    private String  content;

    @Column(nullable = false)
    private long vote = 0L;

    @Column(nullable = false)
    private boolean deleted = false;

    private boolean anonymous = false;
}

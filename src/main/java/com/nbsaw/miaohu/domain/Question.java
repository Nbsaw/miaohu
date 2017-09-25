package com.nbsaw.miaohu.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Question implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String uid;

    @Column(nullable = false,unique = true)
    String title;

    @Lob
    @Column(length = 1000000)
    String content;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();

    boolean anonymous;
}

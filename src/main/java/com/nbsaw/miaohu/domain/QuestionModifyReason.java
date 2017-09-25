package com.nbsaw.miaohu.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class QuestionModifyReason implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    Long questionId;

    @Column(nullable = false)
    String reason;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();
}

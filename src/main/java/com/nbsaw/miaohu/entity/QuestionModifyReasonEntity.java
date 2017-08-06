package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "question_modify_reason")
@Data
public class QuestionModifyReasonEntity implements Serializable {
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

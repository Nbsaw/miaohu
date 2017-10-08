package com.nbsaw.miaohu.dao.repository.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class AnswerVote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Answer answer;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

}

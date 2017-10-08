package com.nbsaw.miaohu.dao.repository.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Answer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private Question question;

    @ManyToOne(optional = false)
    private User user;

    @Type(type = "text")
    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    private boolean anonymous = false;
}

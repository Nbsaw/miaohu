package com.nbsaw.miaohu.dao.repository.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToMany
    private List<Tag> tags;

    @Column(nullable = false , unique = true)
    private String title;

    @Lob
    @Column(length = 1000000)
    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    private boolean anonymous;
}

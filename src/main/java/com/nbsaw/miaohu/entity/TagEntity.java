package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tag")
@Data
public class TagEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    private String bio;

    private Long count = 0L;

    @Column(nullable = false)
    private String avatar;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();
}

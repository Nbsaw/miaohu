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
    Long id;

    @Column(nullable = false,unique = true)
    String name;

    String bio;

    Long count = 0L;

    @Column(nullable = false)
    String avatar;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();
}

package com.nbsaw.miaohu.model;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Tag implements Serializable {
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

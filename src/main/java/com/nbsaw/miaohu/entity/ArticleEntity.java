package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "article")
@Data
public class ArticleEntity {
    @Id
    @GeneratedValue
    private long id;

    private UUID uid;

    @Column(length = 51,nullable = false)
    private String title;

    private boolean anonymous;
}

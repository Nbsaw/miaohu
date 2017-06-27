package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.UUID;

/**
 * Created by fz on 17-3-31.
 */
@Entity
@Table(name = "article")
@Data
public class ArticleEntity {
    @Id
    @GeneratedValue
    // 文章id
    private long id;

    // 用户id
    private UUID uid;

    @Column(length = 51,nullable = false)
    // 标题
    private String title;

    // 匿名
    private boolean anonymous;
}

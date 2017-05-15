package com.miaohu.domain.article;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by fz on 17-3-31.
 */
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue
    private long id;    // 文章id,自增
    private UUID uid;    // 用户id
    @Column(length = 51,nullable = false)
    private String title;    // 知乎51个字限制,标题不能为空
    private boolean anonymous; //是否为匿名

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}

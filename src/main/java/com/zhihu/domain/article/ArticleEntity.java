package com.zhihu.domain.article;

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
    private UUID userid;    // 用户id
    @Column(name = "title",length = 51,nullable = false)
    private String title;    // 知乎51个字限制,标题不能为空
    private String topic;    //话题
    private boolean anonymous; //是否为匿名

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUserid() {
        return userid;
    }

    public void setUserid(UUID userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}

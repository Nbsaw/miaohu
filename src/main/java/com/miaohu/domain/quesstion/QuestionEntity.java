package com.miaohu.domain.quesstion;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fz on 17-3-31.
 */
@Entity
@Table(name = "question")
public class QuestionEntity {
    // 问题id
    @Id
    @GeneratedValue
    private Long id;

    // 用户id
    @Column(nullable = false)
    private String uid;

    // 问题标题
    @Column(nullable = false,unique = true)
    private String title;

    // 问题内容
    @Lob
    @Column(length = 1000000)
    private String  content;

    // 发表时间
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    // 匿名
    private boolean anonymous;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

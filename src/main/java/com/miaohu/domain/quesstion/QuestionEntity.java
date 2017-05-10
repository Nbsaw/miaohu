package com.miaohu.domain.quesstion;

import javax.persistence.*;
/**
 * Created by fz on 17-3-31.
 */
@Entity
@Table(name = "question" ) // , indexes = {@Index(name = "tags_index",columnList = "tag",unique = true)}
public class QuestionEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false,unique = true)
    private String title;

    @Lob
    @Column(length = 1000000)
    private String  content;

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

}

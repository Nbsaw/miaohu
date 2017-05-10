package com.miaohu.domain.comment;

import javax.persistence.*;

/**
 * Created by Nbsaw on 17-5-3.
 */
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue
    private long id;

    // 关联id
    @Column(nullable = false)
    private Long correlation;

    private String uid;

    // 类型
    private String type;

    @Lob
    @Column(length = 1000000)
    private String  content;

    private long vote;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCorrelation() {
        return correlation;
    }

    public void setCorrelation(Long correlation) {
        this.correlation = correlation;
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

    public long getVote() {
        return vote;
    }

    public void setVote(long vote) {
        this.vote = vote;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

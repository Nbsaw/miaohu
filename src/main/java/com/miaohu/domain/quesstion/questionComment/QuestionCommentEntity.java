package com.miaohu.domain.quesstion.questionComment;

import javax.persistence.*;

/**
 * Created by Nbsaw on 17-5-3.
 */
@Entity
@Table(name = "question_comment")
public class QuestionCommentEntity {
    @Id
    @GeneratedValue
    private long id;

    // 问题的Id
    @Column(nullable = false)
    private Long questionId;

    // 用户id
    @Column(nullable = false)
    private String uid;

    // 问题内容
    @Lob
    @Column(length = 1000000,nullable = false)
    private String  content;

    // 点赞次数
    @Column(nullable = false)
    private long vote = 0L;

    // 删除状态
    @Column(nullable = false)
    private boolean deleted = false;

    // 匿名状态
    private boolean anonymous = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}

package com.miaohu.nbsaw.vo;

import com.miaohu.nbsaw.domain.TagEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by nbsaw on 2017/5/16.
 */
public class QuestionVo {
    private Long id;

    private String title;

    private String content;

    private Date date;

    private List<TagEntity> tag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<TagEntity> getTag() {
        return tag;
    }

    public void setTag(List<TagEntity> tag) {
        this.tag = tag;
    }
}

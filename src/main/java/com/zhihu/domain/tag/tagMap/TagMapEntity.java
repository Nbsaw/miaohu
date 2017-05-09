package com.zhihu.domain.tag.tagMap;

import javax.persistence.*;

/**
 * Created by Nbsaw on 17-5-5.
 */
@Entity
@Table(name = "tag_map")
public class TagMapEntity {
    @Id
    @GeneratedValue
    private Long id;
    // 关联id
    @Column(nullable = false)
    private Long correlation;

    // 标签id
    @Column(nullable = false)
    private Long tagId;

    // 类型
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCorrelation() {
        return correlation;
    }

    public void setCorrelation(Long correlation) {
        this.correlation = correlation;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

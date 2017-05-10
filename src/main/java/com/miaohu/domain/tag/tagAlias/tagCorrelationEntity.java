package com.miaohu.domain.tag.tagAlias;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Nbsaw on 17-5-7.
 */
@Entity
@Table(name = "tag_correlation")
public class tagCorrelationEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String tagName;

    private String correlation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCorrelation() {
        return correlation;
    }

    public void setCorrelation(String correlation) {
        this.correlation = correlation;
    }
}

package com.nbsaw.miaohu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tag_correlation")
// 关联标签表
// 例如 js -> javascript , node -> node.js
public class TagCorrelationEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String tagName;

    private String correlation;
}

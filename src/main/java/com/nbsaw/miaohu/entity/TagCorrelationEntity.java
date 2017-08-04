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
    Long id;

    String tagName;

    String correlation;
}

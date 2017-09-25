package com.nbsaw.miaohu.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
// 关联标签表
// 例如 js -> javascript , node -> node.js
public class TagCorrelation implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    String tagName;

    String correlation;
}

package com.nbsaw.miaohu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Nbsaw on 17-5-7.
 */
@Entity
@Table(name = "tag_correlation")
public class TagCorrelationEntity {
    @Id
    @GeneratedValue
    // 标签关联表id
    private Long id;

    // 标签名
    private String tagName;

    // 关联id
    private String correlation;
}

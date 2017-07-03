package com.nbsaw.miaohu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tag_correlation")
public class TagCorrelationEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String tagName;

    private String correlation;
}

package com.nbsaw.miaohu.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "education")
@Data
public class EducationEntity implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    String uid;

    String description;
}

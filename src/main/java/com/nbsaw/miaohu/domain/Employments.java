package com.nbsaw.miaohu.domain;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Employments implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    String uid;

    String description;
}
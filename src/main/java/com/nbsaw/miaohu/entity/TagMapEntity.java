package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "tag_map")
@Data
public class TagMapEntity {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    Long correlation;

    @Column(nullable = false)
    Long tagId;

    String type;
}

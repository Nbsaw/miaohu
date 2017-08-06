package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tag_map")
@Data
public class TagMapEntity implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    Long correlation;

    @Column(nullable = false)
    Long tagId;

    String type;
}

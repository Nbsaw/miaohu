package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tag_map")
@Data
public class TagMapEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long correlation;

    @Column(nullable = false)
    private Long tagId;

    private String type;

}

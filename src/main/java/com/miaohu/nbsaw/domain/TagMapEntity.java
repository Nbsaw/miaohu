package com.miaohu.nbsaw.domain;

import lombok.Data;
import javax.persistence.*;

/**
 * Created by Nbsaw on 17-5-5.
 */
@Entity
@Table(name = "tag_map")
@Data
public class TagMapEntity {
    @Id
    @GeneratedValue
    // 标签引射id
    private Long id;

    // 关联id
    @Column(nullable = false)
    private Long correlation;

    // 标签id
    @Column(nullable = false)
    private Long tagId;

    // 类型
    private String type;
}

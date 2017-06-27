package com.nbsaw.miaohu.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * Created by Nbsaw on 17-5-5.
 */
@Entity
@Table(name = "tag")
@Data
public class TagEntity {
    @Id
    @GeneratedValue
    // 标签id
    private Long id;

    // 标签的名字
    @Column(nullable = false,unique = true)
    private String name;

    // 标签描述
    private String bio;

    // 标签的引用个数
    private Long count = 0L;

    // 标签的头像,已设置默认标签
    @Column(nullable = false)
    private String avatar;
}

package com.zhihu.domain.tag;

import javax.persistence.*;

/**
 * Created by Nbsaw on 17-5-5.
 */
@Entity
@Table(name = "tag")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

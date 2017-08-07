package com.nbsaw.miaohu.entity;

import com.nbsaw.miaohu.type.SexType;
import com.nbsaw.miaohu.type.UserType;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Formula;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
// 教育经历,职业经历为列表已抽出
public class UserEntity implements Serializable {
    @Id
    String id;

    @Column(nullable = false, length = 16)
    String username;

    @Column(nullable = false)
    @ColumnTransformer(write = "md5(?)")
    String password;

    @Column(unique = true)
    String phone;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date creationDate = new Date();

    @Enumerated(EnumType.STRING)
    UserType userType = UserType.USER;

    @Column(name = "sex")
    @Enumerated(EnumType.ORDINAL)
    SexType sex = SexType.male;

    @Column(name = "avatar")
    String avatar = "http://7xqvgr.com1.z0.glb.clouddn.com/01.png";

    String banner;

    String bio = "这个人不懒但是什么也没留下";

    // 居住地
    String domicile;

    // 网站个人域名
    String domain;

    // 行业
    String industry;

    // 简历
    String resume;
}
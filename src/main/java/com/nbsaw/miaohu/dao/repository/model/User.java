package com.nbsaw.miaohu.dao.repository.model;

import com.nbsaw.miaohu.type.SexType;
import com.nbsaw.miaohu.type.UserType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false , length = 16)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true,length = 11)
    private String phone;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;

    @Column(name = "sex")
    @Enumerated(EnumType.ORDINAL)
    private SexType sex = SexType.male;

    @Column(name = "avatar")
    private String avatar = "http://7xqvgr.com1.z0.glb.clouddn.com/01.png";

    private String banner;

    private String bio = "这个人不懒但是什么也没留下";

    // 网站个人域名
    private String domain;

    // 行业
    private String industry;

    // 简历
    private String resume;
}
package com.nbsaw.miaohu.entity;

import com.nbsaw.miaohu.type.UserType;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class UserEntity implements Serializable {
    @Id
    private String id;

    @Column(nullable = false, length = 16)
    private String username;

    @Column(nullable = false)
    @ColumnTransformer(write = "md5(?)")
    private String password;

    @Column(unique = true)
    private String phone;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;

    @Min(1)
    @Max(150)
    private Integer age;

    private String sex = "男";

    @Column(name = "avatar")
    private String avatar = "http://7xqvgr.com1.z0.glb.clouddn.com/01.png";

    private String bio = "这个人不懒但是什么也没留下";

    private String domain;

    private String education;

    private String domicile;

}
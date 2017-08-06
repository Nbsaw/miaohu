package com.nbsaw.miaohu.entity;

import com.nbsaw.miaohu.converter.SexTypeConverter;
import com.nbsaw.miaohu.type.SexType;
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

    @Min(1)
    @Max(150)
    Integer age;

    @Column(name = "sex")
    @Convert(converter = SexTypeConverter.class)
    SexType sex;

    @Column(name = "avatar")
    String avatar = "http://7xqvgr.com1.z0.glb.clouddn.com/01.png";

    String bio = "这个人不懒但是什么也没留下";

    String domain;

    String education;

    String domicile;
}
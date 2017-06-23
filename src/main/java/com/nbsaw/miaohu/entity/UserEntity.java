package com.nbsaw.miaohu.entity;

import com.nbsaw.miaohu.type.UserType;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fz on 17-3-24.
 */
@Entity
@Table(name = "user")
@Data
public class UserEntity implements Serializable {
    /**
     * 以下为账号信息
     */
    @Id
    private String id; //用户id

    @Column(nullable = false, length = 16)
    private String username; //用户名

    @Column(nullable = false)
    @ColumnTransformer(write = "md5(?)")
    private String password; //密码

    private String phone; //手机号码

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date(); //创建日期

    // 用户权限
    @Enumerated(EnumType.STRING) //字符串形式
    private UserType userType = UserType.USER;

    /**
     * 以下为用户信息
     */

    @Min(1)
    @Max(150)
    private Integer age; //年龄

    private String sex = "男"; //性别

    @Column(name = "avatar")
    private String avatar = "http://7xqvgr.com1.z0.glb.clouddn.com/01.png"; //头像

    private String bio = "这个人不懒但是什么也没留下"; //个人简历

    private String domain; //个人网站

    private String education; //教育

    private String domicile; //居住地

}
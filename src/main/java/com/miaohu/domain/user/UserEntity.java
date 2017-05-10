package com.miaohu.domain.user;

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
public class UserEntity implements Serializable {
    /**
     * 以下为账号信息
     */
    @Id
    private String id; //用户id

    @Column(nullable = false, length = 16)
    private String username; //用户名

    @Column(nullable = false, length = 16)
    private String password; //密码

    private String phone; //手机号码

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date(); //创建日期


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }
}
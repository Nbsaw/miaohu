package com.miaohu.service.getUserInfo;

/**
 * Created by fz on 17-3-21.
 * 必要的用户信息
 */

public class UserInfoVO {
    // 用户名
    private String username;
    // 用户头像
    private String avatar;
    // 用户位置
    private String location;
    // 用户性别
    private String sex;
    // 用户简历
    private String bio;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}

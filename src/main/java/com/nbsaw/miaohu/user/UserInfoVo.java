package com.nbsaw.miaohu.user;

import lombok.Data;

/**
 * Created by fz on 17-3-21.
 * 可以暴露出去的用户信息
 */
@Data
public class UserInfoVo {
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
}

package com.nbsaw.miaohu.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserInfoVo implements Serializable {
    private String username;

    private String avatar;

    private String location;

    private String sex;

    private String bio;
}

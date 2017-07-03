package com.nbsaw.miaohu.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserInfoModel implements Serializable {
    private String username;

    private String avatar;

    private String location;

    private String sex;

    private String bio;
}

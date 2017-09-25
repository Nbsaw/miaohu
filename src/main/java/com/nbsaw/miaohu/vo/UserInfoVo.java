package com.nbsaw.miaohu.vo;

import com.nbsaw.miaohu.domain.Domicile;
import com.nbsaw.miaohu.domain.Education;
import com.nbsaw.miaohu.domain.Employments;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class UserInfoVo implements Serializable {
    private String username;

    private String avatar;

    private String sex;

    private String bio;

    private String industry;

    private String resume;

    private List<Employments> employments;

    private List<Education> education;

    private List<Domicile> domicile;
}

package com.nbsaw.miaohu.vo;

import com.nbsaw.miaohu.entity.DomicileEntity;
import com.nbsaw.miaohu.entity.EducationEntity;
import com.nbsaw.miaohu.entity.EmploymentsEntity;
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

    private List<EmploymentsEntity> employments;

    private List<EducationEntity> education;

    private List<DomicileEntity> domicile;
}

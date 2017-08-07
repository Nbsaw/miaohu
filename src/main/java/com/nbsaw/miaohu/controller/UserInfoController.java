package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.vo.MessageVo;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/user")
// 主要防止注入。
public class UserInfoController {

    // 资料修改
    // TODO 性别
    public MessageVo changeSex(@Param("sex") String sex, HttpServletRequest request){
        return null;
    }
    // TODO 一句话介绍
    public MessageVo changeBio(@Param("bio") String bio, HttpServletRequest request){
        return null;
    }
    // TODO 居住地
    public MessageVo changeDomicile(@Param("domicile") String domicile, HttpServletRequest request){
        return null;
    }
    // TODO 所在行业
    public MessageVo changeIndustry(@Param("industry") String industry,HttpServletRequest request){
        return null;
    }
    // TODO 职业经历，可以包含多个

    // TODO 教育经历
    public MessageVo changeEducation(@Param("education") String education,HttpServletRequest request){
        return null;
    }
    // TODO 个人简介
    public MessageVo changeResume(@Param("resume") String resume,HttpServletRequest request){
        return null;
    }
    // TODO 头像修改
}

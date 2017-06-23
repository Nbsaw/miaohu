package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nbsaw on 2017/6/11.
 */
@RestController
@RequestMapping("/token")
public class TokenController  {
    @Autowired
    private JwtUtil jwtUtil;

    // 根据账号密码登陆获取token
    @RequestMapping
    public ResultVo getToken(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
//        resultVo.setResult(jwtUtil.createJWT());
        return resultVo;
    }
}

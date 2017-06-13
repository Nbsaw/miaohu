package com.nbsaw.miaohu.user;

import com.nbsaw.miaohu.util.JwtUtil;
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

    /**
     *  第一次登陆时候的token
     */
    @RequestMapping
    public String getToken(){
        return jwtUtil.createJWT();
    }

    @PostMapping("/valid")
    public String getId(String jwt){
        return jwtUtil.parse(jwt).getId();
    }
}

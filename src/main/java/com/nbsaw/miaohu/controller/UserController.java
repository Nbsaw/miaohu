package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.repository.*;
import com.nbsaw.miaohu.entity.UserEntity;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserRepository        userRepository;
    @Autowired private JwtUtil               jwtUtil;

    // TODO 第三方登录
    // 登录验证
    @PostMapping("/login")
    public GenericVo login(@RequestParam String phone ,
                           @RequestParam String password){
        // 检查手机号码是否存在
        if (userRepository.isUserExists(phone)){
            try{
                // 校对用户手机号码密码
                UserEntity userEntity = userRepository.login(phone,password);
                ResultVo resultVo = new ResultVo();
                resultVo.setCode(200);
                String token = jwtUtil.createJWT(userEntity.getId(),userEntity.getUserType());
                resultVo.setResult(token);
            return resultVo;}
            catch (Exception e){
                MessageVo messageVo = new MessageVo();
                messageVo.setCode(400);
                messageVo.setMessage("用户密码错误");
                return messageVo;
            }
        }
        else{
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(400);
            messageVo.setMessage("用户不存在");
            return messageVo;
        }
    }

    // TODO 注销登陆
    // 从redis删除token
}
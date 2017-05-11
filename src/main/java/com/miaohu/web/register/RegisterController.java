package com.miaohu.web.register;

import com.miaohu.config.redis.RedisConfig;
import com.miaohu.util.constant.RedisConstant;
import com.miaohu.domain.user.UserEntity;
import com.miaohu.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by fz on 17-4-9.
 */
@RestController
@RequestMapping(value = "/register")
public class RegisterController {
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private UserRepository userRepository;

    // 第一步检测
    @PostMapping(value = "/valid",produces="application/json;charset=UTF-8")
    public Map valid(RegisterForm registerForm, HttpSession session) {
        return validate(registerForm, session,false);
    }

    // 检测参数是否合法
    public Map validate(RegisterForm registerForm, HttpSession session,boolean is) {
        Map result = new LinkedHashMap();
        Map errors = new LinkedHashMap();

        // 需要被检测的信息
        String username = registerForm.getUsername();
        String password = registerForm.getPassword();
        String imageCaptcha = registerForm.getImageCaptcha();
        String redisCaptcha = redisConfig.getTemplate().opsForValue().get(session.getId() + RedisConstant.IMAGE);
        String phone = registerForm.getPhone();

        // 校验用户名
        RegisterValidUtil.usernameValid(username, errors);

        // 校验密码
        RegisterValidUtil.passwordValid(password, errors);

        // 校验手机号码
        RegisterValidUtil.phoneValid(phone, errors,userRepository);

        // 校验图片验证码
        RegisterValidUtil.imageCaptchaValid(imageCaptcha, errors, redisCaptcha);

        // 校验手机验证码
        if (is == true){
            String redisPhoneCaptcha = (String) redisConfig.getTemplate().opsForHash().get(session.getId()+phone+ RedisConstant.PHONE,"value");
            RegisterValidUtil.phoneCaptchaValid(registerForm.getPhoneCaptcha(),errors,redisPhoneCaptcha);
        }

        // 判断是否有错误信息
        if (!errors.isEmpty()) {
            result.put("code", 400);
            result.put("errors", errors);
        } else {
            result.put("code", 200);
            result.put("msg", "验证通过!");
        }

        return result;
    }

    // 正式注册
    @PostMapping(produces="application/json;charset=UTF-8")
    public Map register(RegisterForm registerForm, HttpSession session) {

        // 校对用户信息
        Map result = validate(registerForm, session,true);

        // 验证不通过的情况
        if ((int) result.get("code") == 200){
            // 把注册信息写入到数据库
            UserEntity userEntity = new UserEntity();
            userEntity.setId(UUID.randomUUID().toString());
            userEntity.setUsername(registerForm.getUsername());
            userEntity.setPassword(registerForm.getPassword());
            userEntity.setPhone(registerForm.getPhone());
            userRepository.save(userEntity);
            result.put("msg", "注册成功!!");
        }
        return result;
    }
}

package com.miaohu.web.user;

import com.miaohu.domain.user.UserEntity;
import com.miaohu.domain.user.UserRepository;
import com.miaohu.service.getUserInfo.UserInfoService;
import com.miaohu.service.getUserInfo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import static com.miaohu.domain.user.UserType.LOCAL;


/**
 * Created by fz on 17-3-22.
 * 用户rest路由控制器
 * 处理用户的信息
 */

@RestController
@RequestMapping(value = "/user")

public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserRepository userRepository;

    /**
     * 登录接口,判断手机和密码,把信息存在session里面
     * @param session
     * @param phone
     * @param password
     * @return
     */
    // TODO 加密解密,反之中间人攻击
    @GetMapping(value = "/login",produces="application/json;charset=UTF-8")
    public Map login(HttpSession session, @RequestParam("phone") String phone , @RequestParam("password") String password){
        Map result = new LinkedHashMap();
        try {
            UserEntity userEntity = userRepository.login(phone,password);
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setId(userEntity.getId());
            userInfoVO.setUsername(userEntity.getUsername());
            userInfoVO.setAvatar(userEntity.getAvatar());
            userInfoVO.setSex(userEntity.getSex());
            session.setAttribute("data",userInfoVO);
            session.setAttribute("oauth_type",LOCAL);
            result.put("code",200);
            result.put("data",userInfoVO);
        }catch (Exception e){
            result.put("code",400);
            result.put("errors","用户名或者密码错误");
        }
        return result;
    }

    // 获取用户信息
    @GetMapping(value = "/info",produces="application/json;charset=UTF-8")
    public Map information(HttpSession session){
        Map result = new LinkedHashMap();
        // 从Session获取已经存在的用户access_token,用户类型
        try {
            result.put("code",200);
            result.put("data",userInfoService.getUserInfo(session));
        }catch (Exception e){
            result.put("code",401);
            result.put("errors","没登录拿个猫的信息");
        }
        return result;
    }

    // 账号注销,注销后跳转到首页
    @GetMapping("/logout")
    public void  logout(HttpServletResponse response) throws IOException {
        response.setHeader("Set-Cookie","miaohu=ass;httpOnly=true;path=/;Max-age=-1");
        response.sendRedirect("/");
    }
}

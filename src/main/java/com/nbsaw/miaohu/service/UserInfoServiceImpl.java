package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.user.UserRepository;
import com.nbsaw.miaohu.user.UserInfoVo;
import com.nbsaw.miaohu.user.UserEntity;
import com.nbsaw.miaohu.user.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Map;

/**
 * Created by fz on 17-3-27.
 */
@Component
public class UserInfoServiceImpl implements UserInfoService, GetUserInfoService {
    // 用户
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfoVo getUserInfo(HttpSession session) {
        UserInfoVo userInfoVo = (UserInfoVo) session.getAttribute("userInfo");
        if (userInfoVo == null) {
            UserType userType = (UserType) session.getAttribute("oauth_type");
            switch (userType) {
                case LOCAL:
                    userInfoVo = getLocalUser(session);
                    break;
                case GITHUB:
                    userInfoVo = getGithubUser(session);
                    break;
            }
        }
        return userInfoVo;
    }

    @Override
    public UserInfoVo getGithubUser(HttpSession session) {
        // 获取用户access_token
        String access_token = (String) session.getAttribute("access_token");
        URI targetUrl = UriComponentsBuilder.fromUriString("https://api.github.com")
                .path("/user")
                .queryParam("access_token", access_token)
                .build()
                .toUri();
        System.out.println(access_token);
        RestTemplate template = new RestTemplate();
        // 通过access_token从github获取用户信息
        Map auth_result = template.getForObject(targetUrl, Map.class);
        // 封装了用户的必要信息,获取相关字段从github
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUsername((String) auth_result.get("login"));
        userInfoVo.setAvatar((String) auth_result.get("avatar_url"));
        userInfoVo.setLocation((String) auth_result.get("location"));
        session.setAttribute("userInfo", userInfoVo);
        return userInfoVo;
    }

    // TODO 修正
    @Override
    public UserInfoVo getLocalUser(HttpSession session) {
        //获取用户session
        String access_token = (String) session.getAttribute("access_token");
        String uid = (String) session.getAttribute("id");
        UserEntity userEntity = userRepository.findOne(uid);
        UserInfoVo userInfoVo = new UserInfoVo();
        // TODO 本地位置
        userInfoVo.setUsername(userEntity.getUsername());
        userInfoVo.setSex(userInfoVo.getSex());
        userInfoVo.setBio(userEntity.getBio());
        userInfoVo.setAvatar(userEntity.getAvatar());
        return userInfoVo;
    }
}

package com.nbsaw.miaohu.serviceImpl;

import com.nbsaw.miaohu.service.GetUserInfoService;
import com.nbsaw.miaohu.service.UserInfoService;
import com.nbsaw.miaohu.repository.UserRepository;
import com.nbsaw.miaohu.model.UserInfoModel;
import com.nbsaw.miaohu.entity.UserEntity;
import com.nbsaw.miaohu.type.UserType;
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
    public UserInfoModel getUserInfo(HttpSession session) {
        UserInfoModel userInfoModel = (UserInfoModel) session.getAttribute("userInfo");
        if (userInfoModel == null) {
            UserType userType = (UserType) session.getAttribute("oauth_type");
            switch (userType) {
                case LOCAL:
                    userInfoModel = getLocalUser(session);
                    break;
                case GITHUB:
                    userInfoModel = getGithubUser(session);
                    break;
            }
        }
        return userInfoModel;
    }

    @Override
    public UserInfoModel getGithubUser(HttpSession session) {
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
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUsername((String) auth_result.get("login"));
        userInfoModel.setAvatar((String) auth_result.get("avatar_url"));
        userInfoModel.setLocation((String) auth_result.get("location"));
        session.setAttribute("userInfo", userInfoModel);
        return userInfoModel;
    }

    // TODO 修正
    @Override
    public UserInfoModel getLocalUser(HttpSession session) {
        //获取用户session
        String access_token = (String) session.getAttribute("access_token");
        String uid = (String) session.getAttribute("id");
        UserEntity userEntity = userRepository.findOne(uid);
        UserInfoModel userInfoModel = new UserInfoModel();
        // TODO 本地位置
        userInfoModel.setUsername(userEntity.getUsername());
        userInfoModel.setSex(userInfoModel.getSex());
        userInfoModel.setBio(userEntity.getBio());
        userInfoModel.setAvatar(userEntity.getAvatar());
        return userInfoModel;
    }
}

package com.miaohu.service.getUserInfo;

import com.miaohu.domain.user.UserType;
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
    @Override
    public UserInfoVO getUserInfo(HttpSession session) {
        UserInfoVO userInfoVO = (UserInfoVO) session.getAttribute("userInfo");
        if (userInfoVO == null) {
            UserType userType = (UserType) session.getAttribute("oauth_type");
            switch (userType) {
                case LOCAL:
                    userInfoVO = getLocalUser(session);
                    break;
                case GITHUB:
                    userInfoVO = getGithubUser(session);
                    break;
            }
        }
        return userInfoVO;
    }

    @Override
    public UserInfoVO getGithubUser(HttpSession session) {
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
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername((String) auth_result.get("login"));
        userInfoVO.setAvatar((String) auth_result.get("avatar_url"));
        userInfoVO.setLocation((String) auth_result.get("location"));
        session.setAttribute("userInfo", userInfoVO);
        return userInfoVO;
    }

    @Override
    public UserInfoVO getLocalUser(HttpSession session) {
        //获取用户session
        String access_token = (String) session.getAttribute("access_token");
        UserInfoVO userInfoVO = (UserInfoVO) session.getAttribute("data");
        return userInfoVO;
    }
}

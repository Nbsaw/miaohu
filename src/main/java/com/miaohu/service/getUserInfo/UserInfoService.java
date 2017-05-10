package com.miaohu.service.getUserInfo;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by fz on 17-3-27.
 */
@Service
public interface UserInfoService {
    UserInfoVO getUserInfo(HttpSession session);
}

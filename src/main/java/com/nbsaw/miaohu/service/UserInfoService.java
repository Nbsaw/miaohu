package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.model.UserInfoModel;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by fz on 17-3-27.
 */
@Service
public interface UserInfoService {
    UserInfoModel getUserInfo(HttpSession session);
}

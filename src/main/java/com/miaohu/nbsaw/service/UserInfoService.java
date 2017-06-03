package com.miaohu.nbsaw.service;

import com.miaohu.nbsaw.vo.UserInfoVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by fz on 17-3-27.
 */
@Service
public interface UserInfoService {
    UserInfoVo getUserInfo(HttpSession session);
}

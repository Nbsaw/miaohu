package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.vo.UserInfoVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by fz on 17-3-27.
 */
@Service
public interface UserInfoService {
    UserInfoVo getUserInfo(HttpSession session);
}

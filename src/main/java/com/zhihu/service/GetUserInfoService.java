package com.zhihu.service;

import com.zhihu.vo.UserInfoVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by fz on 17-3-28.
 */
@Service
public interface GetUserInfoService {
    UserInfoVO getGithubUser(HttpSession session);
    UserInfoVO getLocalUser(HttpSession session);
}

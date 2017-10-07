package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.model.User;

public interface RegisterService {

    User register(String username,String password,String phone);

}

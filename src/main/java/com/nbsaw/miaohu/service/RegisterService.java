package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.dao.repository.model.User;

public interface RegisterService {

    User register(String username,String password,String phone);

}

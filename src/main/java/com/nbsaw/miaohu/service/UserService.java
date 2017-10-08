package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.dao.repository.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User login(String phone,String password);

    boolean existsByPhone(String phone);
}

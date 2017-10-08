package com.nbsaw.miaohu.serviceImpl;

import com.nbsaw.miaohu.dao.repository.UserRepository;
import com.nbsaw.miaohu.dao.repository.model.User;
import com.nbsaw.miaohu.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @_({@Autowired}))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User login(String phone,String password) {
        return userRepository.findByPhoneAndPassword(phone, password);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}

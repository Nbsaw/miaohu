package com.nbsaw.miaohu.serviceImpl;

import com.nbsaw.miaohu.dao.repository.UserRepository;
import com.nbsaw.miaohu.dao.repository.model.User;
import com.nbsaw.miaohu.service.RegisterService;
import com.nbsaw.miaohu.type.UserType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;

    @Override
    public User register(String username,String password,String phone) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setUserType(UserType.USER);
        return userRepository.save(user);
    }
}

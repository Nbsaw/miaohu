package com.nbsaw.miaohu.serviceImpl;

import com.nbsaw.miaohu.dao.UserRepository;
import com.nbsaw.miaohu.model.User;
import com.nbsaw.miaohu.service.RegisterService;
import com.nbsaw.miaohu.type.UserType;
import com.nbsaw.miaohu.common.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;

    @Override
    public User register(String username,String password,String phone) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setUserType(UserType.USER);
        return userRepository.save(user);
    }
}

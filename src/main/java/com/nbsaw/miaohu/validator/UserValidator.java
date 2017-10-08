package com.nbsaw.miaohu.validator;

import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @_({@Autowired}))
public class UserValidator {

    private final UserService userService;

    public ErrorsMap existsValid(String phone){
        ErrorsMap errors = new ErrorsMap();
        if (!userService.existsByPhone(phone)){
            errors.put("user","用户不存在");
        }
        return errors;
    }

}

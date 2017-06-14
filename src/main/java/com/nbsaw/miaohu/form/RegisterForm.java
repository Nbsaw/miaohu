package com.nbsaw.miaohu.form;
import lombok.Data;

/**
 * Created by fz on 17-4-10.
 */
@Data
public class RegisterForm  {
    private String username;
    private String password;
    private String phone;
    private String imageCaptcha;
    private String phoneCaptcha;
}

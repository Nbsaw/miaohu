package com.nbsaw.miaohu.form;

import lombok.Data;

@Data
public class RegisterForm  {
    private String username;

    private String password;

    private String phone;

    private String imageCaptcha;

    private String phoneCaptcha;
}

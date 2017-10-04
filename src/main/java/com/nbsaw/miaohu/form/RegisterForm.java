package com.nbsaw.miaohu.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class RegisterForm  {

    private String username;

    private String password;

    private String phone;

    private String imageCaptcha;

    private String phoneCaptcha;
}

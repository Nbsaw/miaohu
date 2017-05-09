package com.zhihu.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * Created by fz on 17-4-10.
 */
public class RegisterForm  {
    private String username;
    private String password;
    private String phone;
    private String imageCaptcha;

    private String phoneCaptcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageCaptcha() {
        return imageCaptcha;
    }

    public void setImageCaptcha(String imageCaptcha) {
        this.imageCaptcha = imageCaptcha;
    }

    public String getPhoneCaptcha() {
        return phoneCaptcha;
    }

    public void setPhoneCaptcha(String phoneCaptcha) {
        this.phoneCaptcha = phoneCaptcha;
    }
}

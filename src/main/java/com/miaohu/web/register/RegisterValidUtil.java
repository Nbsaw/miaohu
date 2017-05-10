package com.miaohu.web.register;

import com.miaohu.domain.user.UserRepository;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fz on 17-4-12.
 */
public class RegisterValidUtil {
    /**
     * 判断手机号码是否有效
     * 首先检查手机号码是否合法
     * 再检查数据库中是否已经存在改号码
     *
     * @param phone  手机号码
     * @param errors Map对象,会把错误加入到里面
     */
    public static void phoneValid(String phone, Map errors,UserRepository userRepository) {
        // 判断手机号码是否合法
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        if (phone == null || phone.length() == 0) {
            errors.put("phone", "请填写手机号码");
        }
        // 如果手机号码不合法
        else if (!pattern.matcher(phone).matches()) {
            errors.put("phone", "看起来并不是一个有效的手机号码!");
        }
        // 查看该用户是否存在于数据库
        else {
            if (userRepository.isUserExists(phone)) {
                errors.put("phone", "该手机号已经注册");
            }
        }
    }

    /**
     * 判断验证码是否为空已经在常规检测里面了
     * 这里检测了Redis里面的验证码是否超时
     * 以及验证码不为空的情况,将验证码转换为小写
     * 比较redis里面的验证码,不正确则加入错误信息
     *
     * @param imageCaptcha 前端传过来的图片验证码
     * @param errors       错误信息
     * @param redisCaptcha redis里面的图片验证码
     */
    public static  void imageCaptchaValid(String imageCaptcha, Map errors, String redisCaptcha) {
        if (imageCaptcha == null || imageCaptcha.length() == 0) {
            errors.put("imageCaptcha", "请填写验证码");
        }
        // 判断Redis里面的验证码是否为空
        else if (redisCaptcha == null) {
            errors.put("imageCaptcha", "验证码以超时,请刷新");
        }
        // 如果不为空
        else if (imageCaptcha != null) {
            // 将验证码转换为小写
            imageCaptcha = imageCaptcha.toLowerCase();

            // 如果图片验证码不正确
            if (!imageCaptcha.equals(redisCaptcha))
                errors.put("imageCaptcha", "验证码错误");
        }
    }

    /**
     * 判断手机验证码是否正确
     * @param phoneCaptcha
     * @param errors
     * @param redisCaptcha
     */
    public static void phoneCaptchaValid(String phoneCaptcha,Map errors,String redisCaptcha){
        if (phoneCaptcha == null || phoneCaptcha.length() == 0){
            errors.put("phoneCaptcha","请填写手机验证码");
        }
        else if (!phoneCaptcha.equals(redisCaptcha)){
            errors.put("phoneCaptcha","验证码错误");
        }
    }

    /**
     * 验证名字是否为中文或者数字的组合
     * @param str
     * @return
     */
    public static boolean isValidName(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,50}$");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }


    // 判断用户名是否合法
    public static void usernameValid(String username, Map errors) {
        if (username == null || username.length() == 0) {
            errors.put("username", "请填写用户名");
        } else if (username.indexOf(" ") != -1) {
            errors.put("username", "用户名不应该包含空格");
        } else if (!isValidName(username)) {
            errors.put("username", "用户名不应该包含特殊字符");
        } else if (username.length() < 3) {
            errors.put("username", "姓名最短为2个中文或者3个英文");
        } else if (username.length() > 16) {
            errors.put("username", "用户名不应该超过8个中文或16个英文");
        }
    }

    // 判断密码是否小于6位
    public static void passwordValid(String password, Map errors) {
        if (password == null || password.length() == 0) {
            errors.put("password", "请填写用户密码");
        } else if (password.length() < 6) {
            errors.put("password", "密码应该大于6位");
        }
    }
}

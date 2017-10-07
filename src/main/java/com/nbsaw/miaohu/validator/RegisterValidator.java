package com.nbsaw.miaohu.validator;

import com.nbsaw.miaohu.common.CaptchaUtils;
import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.common.StringUtils;
import com.nbsaw.miaohu.dao.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class RegisterValidator {

    private final UserRepository userRepository;
    private final CaptchaUtils captchaUtils;

    // ----------暴露方法----------

    // 验证表单里面的用户名。密码。手机号码。图片验证码
    public ErrorsMap validForm(String username, String password, String phone, String imageCaptcha, String sid){
        ErrorsMap errors = new ErrorsMap();
        usernameValid(username,errors);
        passwordValid(password,errors);
        phoneValid(phone,errors);
        imageCaptchaValid(sid,imageCaptcha,errors);
        return errors;
    }

    // 注册表单验证
    public ErrorsMap registerValid(String username, String password, String phone, String imageCaptcha,String phoneCaptcha,String sid){
        ErrorsMap errors = validForm(username,password,phone,imageCaptcha,sid);
        phoneCaptchaValid(phone,phoneCaptcha,errors);
        return errors;
    }

    // ----------入口方法----------

    // 判断用户名是否合法
    private void usernameValid(String username,ErrorsMap errorsMap) {
        Pattern p = Pattern.compile("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,50}$");
        Matcher m = p.matcher(username);
        int length = m.matches() ? 2 : 3;
        if (StringUtils.isEmpty(username))
            errorsMap.put("username","请填写用户名");
        else if (username.contains(" "))
            errorsMap.put("username","用户名不应该包含空格");
        else if (!isValidUserName(username))
            errorsMap.put("username","用户名不应该包含特殊字符");
        else if (username.length() < length)
            errorsMap.put("username","姓名最短为2个中文或者3个英文");
        else if (username.length() > 16)
            errorsMap.put("username","用户名不应该超过8个中文或16个英文");
    }

    // 判断密码是否合法
    private void passwordValid(String password,ErrorsMap errorsMap) {
        if (StringUtils.isEmpty(password))
            errorsMap.put("password","请填写用户密码");
        else if (password.length() < 6)
            errorsMap.put("password","密码应该大于6位");
    }

    // 手机号码检测
    private void phoneValid(String phone,ErrorsMap errorsMap) {
        if (StringUtils.isEmpty(phone))
            errorsMap.put("phone","请填写手机号码");
        else if (!isValidPhone(phone))
            errorsMap.put("phone","看起来并不是一个有效的手机号码!");
        else if (isPhoneExistsInDB(phone))
            errorsMap.put("phone","该手机号已经注册");
    }

    // 判断图片验证码是否正确
    private void imageCaptchaValid(String sid,String imageCaptcha,ErrorsMap errorsMap) {
        String redisCaptcha = captchaUtils.getRedisImageCaptcha(sid);
        if (StringUtils.isEmpty(imageCaptcha))
            errorsMap.put("imageCaptcha","请填写验证码");
        else if (isCaptchaTimeOut(redisCaptcha))
            errorsMap.put("imageCaptcha","验证码以超时,请刷新");
        else if (imageCaptcha.equals(redisCaptcha))
            errorsMap.put("imageCaptcha","验证码错误");
    }

    // 判断图片验证码是否正确
    // 三个步骤...首先判断是不是空。再判断有没有超时。再判断是否和Redis里面的一致
    public void phoneCaptchaValid(String phone,String phoneCaptcha,ErrorsMap errorsMap) {
        String redisCaptcha = captchaUtils.getRedisPhoneCaptcha(phone);
        if (StringUtils.isEmpty(phoneCaptcha))
            errorsMap.put("phoneCaptcha","请填写手机验证码");
        else if (isPhoneCaptchaTimeOut(redisCaptcha))
            errorsMap.put("phoneCaptcha","验证码以超时,请刷新");
        else if (phone.equals(redisCaptcha))
            errorsMap.put("phoneCaptcha","验证码错误");
    }

    // ----------辅助方法----------

    // 验证是否是合法的名字
    private boolean isValidUserName(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,50}$");
        Matcher m = p.matcher(str);
        if (m.find())
            temp = true;
        return temp;
    }

    // 是否已经有一样的手机号码存在数据库
    private boolean isPhoneExistsInDB(String phone) {
        return userRepository.isUserExists(phone);
    }

    // 判断是否是一个有效的手机号码
    private boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-1,5-9]))\\d{8}$");
        return pattern.matcher(phone).matches();
    }

    // 判断Redis里面的图片验证证是否超时
    private boolean isCaptchaTimeOut(String redisCaptcha){
        return redisCaptcha == null;
    }

    // 判断Redis里面的手机验证是否超时
    private boolean isPhoneCaptchaTimeOut(String redisCaptcha){
        return redisCaptcha == null;
    }

}

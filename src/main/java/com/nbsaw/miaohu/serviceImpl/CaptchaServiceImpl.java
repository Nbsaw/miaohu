package com.nbsaw.miaohu.serviceImpl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.nbsaw.miaohu.common.StringUtils;
import com.nbsaw.miaohu.service.CaptchaService;
import com.nbsaw.miaohu.common.CaptchaUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class CaptchaServiceImpl implements CaptchaService {

    private final DefaultKaptcha defaultKaptcha;
    private final CaptchaUtils captchaUtils;

    @Override
    // 生成图片验证码输出到页面
    public void drawImageCaptcha(String sid, HttpServletResponse response) throws IOException {
        String capText = defaultKaptcha.createText();
        writeImageCaptcha(response,capText);
        captchaUtils.persistImageCaptcha(sid,capText);
    }

    @Override
    // 发送验证码到用户手机
    public Map sendPhoneCaptcha(String phone) {
        Map result = new LinkedHashMap();
        // 检查手机号是否合法
        if(!isValidPhone(phone)){
            result.put("code", 400);
            result.put("phone", "看起来不是一个合法的手机号码:)");
        }
        // 如果在redis练找不到就发送一条到用户手机号上
        else if (captchaUtils.isSentPhoneCaptcha(phone)){
            captchaUtils.persistPhoneCaptcha(phone,"123456");
            result.put("code", 200);
            result.put("msg", "验证码发送成功!");
        }
        // 如果Redis里面读到了验证码，返回已存在，需要60s后才能重发
        else {
            result.put("code", 304);
            result.put("errors", "已发送过验证码,请过60s后重发");
        }
        return result;
    }

    // 把图片验证码作为图片流返回到前端
    private void writeImageCaptcha(HttpServletResponse response,String capText) throws IOException {
        // 设置请求头
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // 写出图片流
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(defaultKaptcha.createImage(capText),"jpg",out);
        out.flush();
    }

    // 判断手机号码是否合法
    private boolean isValidPhone(String phone){
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-1,5-9]))\\d{8}$");
        return pattern.matcher(phone).matches();
    }

}

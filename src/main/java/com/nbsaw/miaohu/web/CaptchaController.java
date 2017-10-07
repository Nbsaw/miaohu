package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.common.JsonResult;
import com.nbsaw.miaohu.service.CaptchaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/captcha")
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class CaptchaController {

    private final CaptchaService service;

    // 用来作为凭证使用的Sid
    @GetMapping("/sid")
    public JsonResult getSid() {
        return new JsonResult(0, "",new HashMap(){{put("sid",UUID.randomUUID().toString());}});
    }

    // 返回一张图片验证码
    @GetMapping
    public void getCaptcha(@RequestParam String sid, HttpServletResponse response) throws IOException {
        service.drawImageCaptcha(sid, response);
    }

    // 发送手机验证码到对应手机
    @GetMapping("/phoneCaptcha")
    public Map code(@RequestParam String phone) {
        return service.sendPhoneCaptcha(phone);
    }

}
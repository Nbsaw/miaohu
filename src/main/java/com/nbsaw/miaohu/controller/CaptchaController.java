package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.service.CaptchaService;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

   @Autowired private CaptchaService captchaService;

   // 用来作为凭证使用的Sid
   @GetMapping("/sid")
   public ResultVo getSid(){
      return new ResultVo(200,UUID.randomUUID().toString());
   }

   // 返回一张图片验证码
   @GetMapping
   public void getCaptcha(@RequestParam String sid, HttpServletResponse response) throws IOException {
         captchaService.drawImageCaptcha(sid,response);
   }

   // 发送手机验证码到对应手机
   @GetMapping("/phoneCaptcha")
   public Map code(@RequestParam String phone) {
      return captchaService.sendPhoneCaptcha(phone);
   }

}

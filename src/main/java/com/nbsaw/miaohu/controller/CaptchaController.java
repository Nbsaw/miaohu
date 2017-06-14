package com.nbsaw.miaohu.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.nbsaw.miaohu.config.RedisConfig;
import com.nbsaw.miaohu.service.PhoneMessageService;
import com.nbsaw.miaohu.repository.UserRepository;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.util.RedisUtil;
import com.nbsaw.miaohu.util.RegisterValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by fz on 17-4-5.
 */
@RestController
@RequestMapping(value = "/captcha")
public class CaptchaController {
   @Autowired private RedisConfig redisConfig;
   @Autowired private DefaultKaptcha defaultKaptcha;
   @Autowired private UserRepository userRepository;
   @Autowired private PhoneMessageService phoneMessageService;
   @Autowired private JwtUtil jwtUtil;
   @Autowired private RedisUtil redisUtil;
   /**
    * 返回一张验证码图片前端。格式为image/jpeg
    * 设置验证码的时效
    * @param response http响应
//    * @param session session对象
    * @return 返回一张验证码图片。格式为image/jpeg
    * @throws IOException
    */
   @GetMapping
   public ModelAndView getCaptcha(HttpServletResponse response) throws IOException {
      // 设置响应
      response.setDateHeader("Expires", 0);
      response.setHeader("Cache-Control",
              "no-store, no-cache, must-revalidate");
      response.addHeader("Cache-Control", "post-check=0, pre-check=0");
      response.setHeader("Pragma", "no-cache");
      response.setContentType("image/jpeg");

//      response.setHeader("token",jwtUtil.createJWT("asd","asd"));

      // 生成随机验证码
      String capText = defaultKaptcha.createText();
      BufferedImage image = defaultKaptcha.createImage(capText);

      // 输出到页面
      ServletOutputStream out = response.getOutputStream();
      ImageIO.write(image,"jpg",out);
//      session.setAttribute("code",capText);
      out.flush();

      // 保存图片验证码到redis,并设置过期时间
//      StringRedisTemplate template= redisConfig.getTemplate();
//      String key = session.getId()+ RedisConstant.IMAGE;
//      ValueOperations obj =  template.opsForValue();
//      obj.set(key,capText.toLowerCase());
//      template.expire(key,RedisConstant.IMAGETIMEOUT, TimeUnit.MINUTES);

      return null;
   }

   /**
    * 发送验证码到接收到的手机号码上
    * 先检测手机号码是不是合法的,不合法会返回错误信息
    * 在数据库里查询手机是否被注册了,如果注册了返回错误信息
    * @param phone 需要发送验证码的手机号码
    * @param session session对象
    * @return 成功的话会返回一条发送成功信息,
    * 如果不成功的话,返回一条错误信息。
    */
   @GetMapping("/phoneCaptcha")
   public Map code(String phone, HttpSession session) {
      Map result = new LinkedHashMap();
      Map errors = new LinkedHashMap();
      // 校验手机号码是否合法
      RegisterValidUtil.phoneValid(phone, errors,userRepository);
      if (!errors.isEmpty()) {
         result.put("code", 400);
         result.put("errors", errors);
      } else {
         // 从redis读取手机验证码
         StringRedisTemplate template = redisConfig.getTemplate();
         HashOperations<String, String, String> obj = template.opsForHash();
         String sessionId = session.getId();
         String phoneCaptchaFormat =  redisUtil.phoneCaptchaFormat(sessionId,phone);
         int phoneTimeOut = redisUtil.getPhoneTimeOut();
         // 如果不存在就发送一条验证码到手机,并保存下来
         // 超过60秒限制可以重新发验证码
         if (obj.get(phoneCaptchaFormat, "value") == null || new Date().getTime() - Long.valueOf(obj.get(phoneCaptchaFormat, "time")) > 60000) {
            // 手机验证码
            String code = phoneMessageService.sendRegisterCode(phone);
            // 设置手机验证码的值以及超时时间
            obj.put(phoneCaptchaFormat, "value", code);
            obj.put(phoneCaptchaFormat, "time", String.valueOf(new Date().getTime()));
            template.expire(sessionId, phoneTimeOut, TimeUnit.MINUTES);
            result.put("code", 200);
            result.put("msg", "验证码发送成功!");
         } else {
            result.put("code", 304);
            result.put("errors", "已发送过验证码,请过60s后重发");
         }
      }
      return result;
   }
}

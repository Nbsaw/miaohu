package com.nbsaw.miaohu.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.nbsaw.miaohu.config.RedisConfig;
import com.nbsaw.miaohu.service.PhoneMessageService;
import com.nbsaw.miaohu.repository.UserRepository;
import com.nbsaw.miaohu.util.*;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/captcha")
class CaptchaController {

   @Autowired private RedisConfig redisConfig;
   @Autowired private DefaultKaptcha defaultKaptcha;
   @Autowired private UserRepository userRepository;
   @Autowired private PhoneMessageService phoneMessageService;
   @Autowired private JwtUtil jwtUtil;
   @Autowired private RedisUtil redisUtil;

   // 用来作为凭证使用的Sid
   @GetMapping(value = "/sid")
   public ResultVo getSid(){
      ResultVo<String> sidVo = new ResultVo();
      sidVo.setCode(200);
      sidVo.setResult(UUID.randomUUID().toString());
      return sidVo;
   }

   // 返回一张图片验证码
   @GetMapping
   public ModelAndView getCaptcha(@RequestParam String sid, HttpServletResponse response) throws IOException {
      // 设置响应
      response.setDateHeader("Expires", 0);
      response.setHeader("Cache-Control",
              "no-store, no-cache, must-revalidate");
      response.addHeader("Cache-Control", "post-check=0, pre-check=0");
      response.setHeader("Pragma", "no-cache");
      response.setContentType("image/jpeg");

      // 生成随机验证码
      String capText = defaultKaptcha.createText();
      BufferedImage image = defaultKaptcha.createImage(capText);

      // 输出到页面
      ServletOutputStream out = response.getOutputStream();
      ImageIO.write(image,"jpg",out);
      out.flush();

      // 保存图片验证码到redis,并设置过期时间
      StringRedisTemplate template= redisConfig.getTemplate();
      String key = redisUtil.imageCaptchaFormat(sid);
      ValueOperations<String,String> obj =  template.opsForValue();
      obj.set(key,capText.toLowerCase());
      template.expire(key,redisUtil.getImageTimeOut(), TimeUnit.MINUTES);

      return null;
   }

   // 发送一条手机验证码
   @GetMapping("/phoneCaptcha")
   public Map code(@RequestParam String sid ,String phone) {
      // 结果集与错误列表初始化
      Map result = new LinkedHashMap();
      Map errors = new LinkedHashMap();

      // 校验手机号码是否合法并记录到错误列表里
      RegisterValidUtil.phoneValid(phone, errors,userRepository);

      // 如果错误列表不为空
      if (!errors.isEmpty()) {
         result.put("code", 400);
         result.put("errors", errors);
      }
      // 如果错误列表为空
      else {
         // 从redis读取手机验证码
         StringRedisTemplate template= redisConfig.getTemplate();
         String phoneCaptchaFormat =  redisUtil.phoneCaptchaFormat(phone);
         String redisCaptcha = template.opsForValue().get(phoneCaptchaFormat);

         // 如果Redis里面读取不到验证码，生成并返回生成成功的结果
         if (redisCaptcha == null) {

            // 生成一个新的手机验证码
            String code = phoneMessageService.sendRegisterCode(phone);

            // 把验证码存到redis
            ValueOperations obj =  template.opsForValue();
            obj.set(phoneCaptchaFormat,code.toLowerCase());

            // 设置验证码的超时时间
            template.expire(phoneCaptchaFormat,redisUtil.getPhoneTimeOut(), TimeUnit.MINUTES);

            //
            result.put("code", 200);
            result.put("msg", "验证码发送成功!");
         }
         // 如果Redis里面读到了验证码，返回已存在，需要60s后才能重发
         else {
            result.put("code", 304);
            result.put("errors", "已发送过验证码,请过60s后重发");
         }

      }
      return result;
   }
}

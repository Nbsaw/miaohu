package com.nbsaw.miaohu.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;
/**
 * Created by fz on 17-3-25.
 * Kaptcha 的配置文件
 */
@Configuration
@EnableAutoConfiguration
public class CaptchaConfig {
    // 图形验证码生成配置
    @Bean(name="captchaProducer")
    @Scope(value = "prototype")
    public DefaultKaptcha getKaptchaBean(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();
        properties.setProperty("kaptcha.border", "no");
//        properties.setProperty("kaptcha.border.color", "black");
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "38");
//        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.obscurificator.impl","com.nbsaw.miaohu.captcha.CustomCaptcha");

//        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty("kaptcha.textproducer.font.size","26");
        properties.setProperty("kaptcha.textproducer.char.space","3");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
//        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑");
        properties.setProperty("kaptcha.textproducer.char.string","0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

        properties.setProperty("kaptcha.background.clear.from","white");
        properties.setProperty("kaptcha.background.clear.to","white");
        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}

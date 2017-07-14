package com.nbsaw.miaohu.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import java.util.Properties;

@Configuration
@EnableAutoConfiguration
class CaptchaConfig {
    @Bean(name="captchaProducer")
    @Scope(value = "prototype")
    public DefaultKaptcha getKaptchaBean(){
        // 生成一个 DefaultKaptcha 的实例
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();

        // 大小设置
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "38");

        // 样式设置
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.background.clear.from","white");
        properties.setProperty("kaptcha.background.clear.to","white");

        // 字体设置
        properties.setProperty("kaptcha.textproducer.char.space","3");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        properties.setProperty("kaptcha.textproducer.font.size","26");

        // 验证码生成的文字设置
        properties.setProperty("kaptcha.textproducer.char.string","0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

        // 将配置导入到 DefaultKaptcha 实例中
        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}

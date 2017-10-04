package com.nbsaw.miaohu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class Application {
    // 启动服务器
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
package com.nbsaw.miaohu;

/**
 * Created by fz on 17-3-1.
 */
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import java.util.Arrays;

/** @SpringBootApplication
 *  相当于配置了以下的配置
 *  @ComponentScan
 *  @Configuration
 *  @EnableAutoConfiguration
 */
@SpringBootApplication
public class Application {
    @Test
    // 查看已经注册的Beans
    public void showAllBeans(){
        ApplicationContext ctx = SpringApplication.run(Application.class, "");
        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    // 启动项目
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}
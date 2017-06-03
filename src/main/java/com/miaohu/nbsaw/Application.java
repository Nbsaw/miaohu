package com.miaohu.nbsaw;

/**
 * Created by fz on 17-3-1.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


/** @SpringBootApplication
 *  equals import
 *  @ComponentScan
 *  @Configuration
 *  @EnableAutoConfiguration
 */

//@ComponentScan
//@Configuration
//@EnableAutoConfiguration
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
//        System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
    }
}
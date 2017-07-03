package server;

import com.nbsaw.miaohu.Application;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import java.util.Arrays;
/**
 * Created by nbsaw on 7/3/2017.
 * Some Server Test .
 */
public class ServerTest {
    @Test
    // 显示所有服务器里面注册过的Bean
    public void showAllBeans(){
        ApplicationContext ctx = SpringApplication.run(Application.class, "");
        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}

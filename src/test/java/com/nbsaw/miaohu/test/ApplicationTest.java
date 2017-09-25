package com.nbsaw.miaohu.test;

import com.nbsaw.miaohu.Application;
import com.nbsaw.miaohu.controller.TagController;
import com.nbsaw.miaohu.service.PhoneMessageService;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
// `test`是提供给travis ci跑的,如果你要跑测试请修改`test`里面的配置
public class ApplicationTest {

    @Autowired
    private WebApplicationContext context;

    @Test
    public void test(){
        ResultVo vo = context.getBean(TagController.class).findAll();
        Assert.assertEquals(vo.getCode(),200);
    }

}

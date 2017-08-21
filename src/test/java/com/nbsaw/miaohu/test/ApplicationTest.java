package com.nbsaw.miaohu.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
// `test`是提供给travis ci跑的,如果你要跑测试请修改`test`里面的配置
// 单独开个测试数据库跑,然后改掉url里面的`miaohu`。否则无法跑起来
public class ApplicationTest {
    @Test
    public void contextLoads() {

    }
}

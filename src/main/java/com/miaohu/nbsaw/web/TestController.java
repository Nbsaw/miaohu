package com.miaohu.nbsaw.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nbsaw on 2017/6/8.
 */
@RestController
public class TestController {
    @RequestMapping(value = "/asd")
//    @ResponseBody
    public  String tsst(){
        return "{a:'123'}";
    }
}

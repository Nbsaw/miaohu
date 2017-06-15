package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.util.JsonUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

/**
 * Created by nbsaw on 2017/6/14.
 */
@RestController
@RequestMapping(value = "/sid")
public class SessionController {
    @GetMapping
    @ResponseBody
    public String getSid(){
        return JsonUtil.formatResult(200,"",UUID.randomUUID().toString());
    }
}

package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.oauth.GithubOAuth;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    private final String CLIENT_ID="9d37c9f54b2009868da8";
    private final String CLIENT_SECRET = "148992f6ac84b23d720c7e828ccd1a66af4da197";

    @GetMapping("/github")
    void callback(@RequestParam String code){
        RestTemplate restTemplate = new RestTemplate();
        GithubOAuth oauth = new GithubOAuth();
        oauth.setClient_id(CLIENT_ID);
        oauth.setClient_secret(CLIENT_SECRET);
        oauth.setCode(code);
        Map map = restTemplate.postForObject("https://github.com/login/oauth/access_token",oauth,Map.class);
        String access_token = (String) map.get("access_token");
//        response.sendRedirect("http://www.miaohu.moe:9090");
    }

}

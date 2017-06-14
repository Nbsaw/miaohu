package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.oauth.GithuOAuth;
import com.nbsaw.miaohu.type.UserType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fz on 17-3-22.
 */
@RestController
@RequestMapping(value="/oauth")
public class OauthController {
    /** github Oauth 回调地址 */
    @GetMapping("/github")
    void callback(HttpSession session, @RequestParam("code") String code, HttpServletResponse response)throws IOException {
        String client_id="9d37c9f54b2009868da8";
        String client_secret = "148992f6ac84b23d720c7e828ccd1a66af4da197";
        RestTemplate restTemplate = new RestTemplate();
        GithuOAuth oauth = new GithuOAuth();
        oauth.setClient_id(client_id);
        oauth.setClient_secret(client_secret);
        oauth.setCode(code);
        Map map = new HashMap<>();
        map = restTemplate.postForObject("https://github.com/login/oauth/access_token",oauth,Map.class);
        String access_token = (String) map.get("access_token");
        session.setAttribute("access_token",access_token);
        session.setAttribute("oauth_type", UserType.GITHUB);
        response.sendRedirect("http://www.miaohu.moe:9090");
    }
}

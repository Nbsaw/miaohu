package com.nbsaw.miaohu.oauth;

import lombok.Data;

/**
 * Created by fz on 17-3-21.
 * 访问Github Oauth 服务器需要用到的字段
 */
@Data
public class GithuOAuth {
    private String client_id;
    private String client_secret;
    private String code;
    private String accept;
}

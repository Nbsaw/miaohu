package com.nbsaw.miaohu.oauth;

import lombok.Data;

@Data
public class GithubOAuth {

    private String client_id;

    private String client_secret;

    private String code;

    private String accept;

}

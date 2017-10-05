package com.nbsaw.miaohu.vo;

import com.nbsaw.miaohu.model.Tag;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleVo {
    private Long id;

    private String title;

    private String content;

    private Date date;

    private List<Tag> tag;

    private UserInfoVo userInfoVo;

    private String type = "article";
}

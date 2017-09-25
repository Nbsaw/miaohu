package com.nbsaw.miaohu.vo;

import com.nbsaw.miaohu.domain.Tag;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class QuestionVo implements Serializable {
    private Long id;

    private String title;

    private String content;

    private Date date;

    private List<Tag> tag;

    private UserInfoVo userInfoVo;

    private String type = "question";
}

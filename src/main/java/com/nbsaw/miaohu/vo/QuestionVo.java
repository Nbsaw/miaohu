package com.nbsaw.miaohu.vo;

import com.nbsaw.miaohu.entity.TagEntity;
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

    private List<TagEntity> tag;

    private UserInfoVo userInfoVo;
}

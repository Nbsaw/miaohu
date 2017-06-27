package com.nbsaw.miaohu.model;

import com.nbsaw.miaohu.entity.TagEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by nbsaw on 2017/5/16.
 */
@Data
public class QuestionModel implements Serializable {
    // 问题id
    private Long id;

    // 问题标题
    private String title;

    // 问题内容
    private String content;

    // 问题发布日期
    private Date date;

    // 标签集
    private List<TagEntity> tag;
}

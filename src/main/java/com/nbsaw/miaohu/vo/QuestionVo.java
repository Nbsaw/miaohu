package com.nbsaw.miaohu.vo;

import com.nbsaw.miaohu.model.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class QuestionVo implements Serializable {

    private Long id;

    private String title;

    private String content;

    private Date date;

    private List<Tag> tag;

    private UserInfoVo userInfoVo;

    private String type = "question";

    public QuestionVo(Long id , String title , String content , Date date , List<Tag> tag){
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.tag = tag;
    }

}

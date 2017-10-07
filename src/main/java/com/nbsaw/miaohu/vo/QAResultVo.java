package com.nbsaw.miaohu.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class QAResultVo implements Serializable{

    private String id;

    private String title;

    private String content;

    private String uid;

    private String date;

    private String type;

    public QAResultVo(String id,String title,String content,String uid,String date,String type){
        this.id = id;
        this.title = title;
        this.content = content;
        this.uid = uid;
        this.date = date;
        this.type = type;
    }

}

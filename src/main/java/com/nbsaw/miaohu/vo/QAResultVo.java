package com.nbsaw.miaohu.vo;

import lombok.Data;
import java.util.Date;

@Data
public class QAResultVo {
    private Long id;

    private String title;

    private String content;

    private String uid;

    private Date date;
}

package com.nbsaw.miaohu.model;

import com.nbsaw.miaohu.type.ReplyStatusType;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Article implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    String uid;

    @Column(length = 51,nullable = false)
    String title;

    @Lob
    @Column(length = 1000000)
    String content;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();

    // 文章图片
    String pic;

    // 权限设置
    // 开放评论，预览评论，关闭评论
    @Enumerated(EnumType.STRING)
    ReplyStatusType replyStatus;
}

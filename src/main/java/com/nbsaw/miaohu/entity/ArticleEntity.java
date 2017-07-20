package com.nbsaw.miaohu.entity;

import com.nbsaw.miaohu.type.ReplyStatusType;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "article")
@Data
public class ArticleEntity {
    @Id
    @GeneratedValue
    private long id;

    private UUID uid;

    @Column(length = 51,nullable = false)
    private String title;

    @Lob
    @Column(length = 1000000)
    private String  content;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    // 文章图片
    private String pic;

    // 权限设置
    // 开放评论，预览评论，关闭评论
    @Enumerated(EnumType.STRING)
    private ReplyStatusType replyStatus;
}

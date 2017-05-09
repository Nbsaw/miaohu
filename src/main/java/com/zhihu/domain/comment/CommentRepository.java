package com.zhihu.domain.comment;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Nbsaw on 17-5-3.
 */
public interface CommentRepository extends Repository<CommentEntity,Long> {
    // 保存
    void save(CommentEntity commentEntity);
    // 查找评论
    List<CommentEntity> findAllByCorrelationAndType(Long id, String type);
}

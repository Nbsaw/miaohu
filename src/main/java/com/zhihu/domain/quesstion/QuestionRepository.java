package com.zhihu.domain.quesstion;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by fz on 17-3-31.
 */
public interface QuestionRepository extends Repository<QuestionEntity,Long> {
    // 保存到数据库
    void save(QuestionEntity questionEntity);
    // 通过Uid查找问题列表
    List<QuestionEntity> findAllByUid(String userId);
    // 通过id查找某个问题
    QuestionEntity findById(Long id);
    // 删除问题
    @Transactional
    Integer deleteById(Long id);
    // 修改问题
    @Transactional
    @Modifying
    @Query("update QuestionEntity q set q.title = :title , q.content = :content where q.uid = :uid and q.id = :id")
    Integer updateContentByIdAndUid(@Param("id") Long id, @Param("uid") String uid,@Param("title") String title,@Param("content") String content);
    // 点赞功能

}

package com.nbsaw.miaohu.question;

import org.springframework.data.domain.Pageable;
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

    // 逆序时间查找问题
    @Query("select q from QuestionEntity q order by q.date desc")
    List<QuestionEntity> findAll(Pageable pageable);

    // 通过Uid查找问题列表
    List<QuestionEntity> findAllByUid(String uid);

    // 通过问题id查找某个问题
    QuestionEntity findById(Long id);

    // 通过id判断问题是否存在
    @Query("select count(q) > 0 from QuestionEntity q where q.id = :id")
    boolean isExists(@Param("id") Long id);

    // 删除问题
    @Transactional
    Integer deleteById(Long id);

    // 修改问题
    @Transactional
    @Modifying
    @Query("update QuestionEntity q set q.title = :title , q.content = :content where q.id = :id and q.uid = :uid ")
    Integer updateContentByIdAndUid(@Param("id") Long id, @Param("uid") String uid,@Param("title") String title,@Param("content") String content);

    // 判断问题是否已经存在
    @Query("select count(q) > 0 from QuestionEntity q where q.title = :title")
    boolean existsQuestion(@Param("title") String title);

    // 查询问题是否为匿名
    @Query("select qc.anonymous from QuestionEntity qc where qc.id = :id")
    boolean isAnonymous(@Param("id") Long id);

    // 把问题设置为匿名
    @Transactional
    @Modifying
    @Query("update QuestionEntity q set q.anonymous = true where q.id = :id and q.uid = :uid")
    Integer setAnonymousTrue(@Param("id") Long id, @Param("uid") String uid);

    // 把问题设置为实名
    @Transactional
    @Modifying
    @Query("update QuestionEntity q set q.anonymous = false where q.id = :id and q.uid = :uid")
    Integer setAnonymousFalse(@Param("id") Long id, @Param("uid") String uid);

}

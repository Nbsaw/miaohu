package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.entity.QuestionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;

public interface QuestionRepository extends Repository<QuestionEntity,Long> {

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */

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

    // 判断问题是否已经存在
    @Query("select count(q) > 0 from QuestionEntity q where q.title = :title")
    boolean existsQuestion(@Param("title") String title);

    // 查询问题是否为匿名
    @Query("select qc.anonymous from QuestionEntity qc where qc.id = :id")
    boolean isAnonymous(@Param("id") Long id);


    /**
     * ---------------------------------------------------------------------------
     *
     *                                 修 改
     *
     * ---------------------------------------------------------------------------
     */

    // 修改问题
    @Transactional
    @Modifying
    @Query("update QuestionEntity q set q.title = :title , q.content = :content where q.id = :id and q.uid = :uid ")
    Integer updateContentByIdAndUid(@Param("id") Long id, @Param("uid") String uid,@Param("title") String title,@Param("content") String content);


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

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 删 除
     *
     * ---------------------------------------------------------------------------
     */

    // 删除问题
    @Transactional
    Integer deleteById(Long id);


    /**
     * ---------------------------------------------------------------------------
     *
     *                                 保 存
     *
     * ---------------------------------------------------------------------------
     */
    // 保存到数据库
    void save(QuestionEntity questionEntity);
}

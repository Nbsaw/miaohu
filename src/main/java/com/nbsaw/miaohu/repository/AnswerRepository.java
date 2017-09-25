package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.domain.Answer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer,Long> {

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */

    // 查找评论
    List<Answer> findAllByQuestionId(Long questionId,Pageable pageable);

    // 根据用户id和问题id查看是否为提问者
    @Query("select count(a) > 0 from Answer a where a.questionId = :questionId and a.uid = :uid")
    boolean isSelf(@Param("questionId") Long questionId,@Param("uid") String uid);

    // 检测是否回答过问题
    @Query("select count(qc) > 0 from Answer qc where qc.questionId = :questionId and qc.uid = :uid")
    boolean isExists(@Param("questionId") Long questionId , @Param("uid") String uid);

    // 查询删除状态
    @Query("select qc.deleted from Answer qc where qc.questionId = :questionId and qc.uid = :uid ")
    boolean isDeleted(@Param("questionId") Long questionId,@Param("uid") String uid);

    // 判断问题是否为匿名
    @Query("select qc.anonymous from Answer qc where qc.questionId = :questionId and qc.uid = :uid")
    boolean isAnonymous(@Param("questionId") Long questionId,@Param("uid") String uid);


    /**
     * ---------------------------------------------------------------------------
     *
     *                                 修 改
     *
     * ---------------------------------------------------------------------------
     */

    // 修改回答
    @Transactional
    @Modifying
    @Query("update Answer qc set qc.content = :content where qc.questionId = :questionId and qc.uid = :uid")
    Integer updateComment(@Param("questionId") Long questionId,@Param("uid") String uid,@Param("content") String content);

    // 设置回答为匿名
    @Transactional
    @Modifying
    @Query("update Answer qc set qc.anonymous = true where qc.questionId = :questionId and qc.uid = :uid")
    Integer setAnonymousTrue(@Param("questionId") Long questionId, @Param("uid") String uid);

    // 设置回答为实名
    @Transactional
    @Modifying
    @Query("update Answer qc set qc.anonymous = false where qc.questionId = :questionId and qc.uid = :uid")
    Integer setAnonymousFalse(@Param("questionId") Long questionId, @Param("uid") String uid);

    // 删除回答(其实是改变回答状态)
    @Transactional
    @Modifying
    @Query("update Answer qc set qc.deleted = true where qc.questionId = :questionId and qc.uid = :uid")
    Integer setDeletedTrue(@Param("questionId") Long questionId,@Param("uid") String uid);

    // 撤销删除
    @Transactional
    @Modifying
    @Query("update Answer qc set qc.deleted = false where qc.questionId = :questionId and qc.uid = :uid")
    Integer setDeletedFalse(@Param("questionId") Long questionId,@Param("uid") String uid);

}

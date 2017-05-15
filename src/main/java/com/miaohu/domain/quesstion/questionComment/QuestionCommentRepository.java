package com.miaohu.domain.quesstion.questionComment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Nbsaw on 17-5-3.
 */
public interface QuestionCommentRepository extends Repository<QuestionCommentEntity,Long> {
    // 保存
    void save(QuestionCommentEntity questionCommentEntity);

    // 查找评论
    List<QuestionCommentEntity> findAllByQuestionId(Long questionId, String type);

    // 检测是否回答过问题
    @Query("select count(qc) > 0 from QuestionCommentEntity qc where qc.questionId = :questionId and qc.uid = :uid")
    boolean isExists(@Param("questionId") Long questionId , @Param("uid") String uid);

    // 修改回答
    @Transactional
    @Modifying
    @Query("update QuestionCommentEntity qc set qc.content = :content where qc.questionId = :questionId and qc.uid = :uid")
    Integer updateComment(@Param("questionId") Long questionId,@Param("uid") String uid,@Param("content") String content);

    // 查询删除状态
    @Query("select qc.deleted from QuestionCommentEntity qc where qc.questionId = :questionId and qc.uid = :uid ")
    boolean isDeleted(@Param("questionId") Long questionId,@Param("uid") String uid);

    // 删除回答(其实是改变回答状态)
    @Transactional
    @Modifying
    @Query("update QuestionCommentEntity qc set qc.deleted = true where qc.questionId = :questionId and qc.uid = :uid")
    Integer setDeletedTrue(@Param("questionId") Long questionId,@Param("uid") String uid);

    // 撤销删除
    @Transactional
    @Modifying
    @Query("update QuestionCommentEntity qc set qc.deleted = false where qc.questionId = :questionId and qc.uid = :uid")
    Integer setDeletedFalse(@Param("questionId") Long questionId,@Param("uid") String uid);

    // 判断问题是否为匿名
    @Query("select qc.anonymous from QuestionCommentEntity qc where qc.questionId = :questionId and qc.uid = :uid")
    boolean isAnonymous(@Param("questionId") Long questionId,@Param("uid") String uid);

    // 设置回答为匿名
    @Transactional
    @Modifying
    @Query("update QuestionCommentEntity qc set qc.anonymous = true where qc.questionId = :questionId and qc.uid = :uid")
    Integer setAnonymousTrue(@Param("questionId") Long questionId, @Param("uid") String uid);

    // 设置回答为实名
    @Transactional
    @Modifying
    @Query("update QuestionCommentEntity qc set qc.anonymous = false where qc.questionId = :questionId and qc.uid = :uid")
    Integer setAnonymousFalse(@Param("questionId") Long questionId, @Param("uid") String uid);
}

package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.entity.AnswerVoteMapEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;

public interface AnswerVoteMapRepository extends Repository<AnswerVoteMapEntity,Long> {
    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */

    // 查看是否点赞
    @Query("select count(a) > 0 from AnswerVoteMapEntity a where a.answerId = :answerId and a.uid = :uid")
    boolean isVote(@Param("answerId") Long answerId , @Param("uid") String uid);

    // 查找questionId
    @Query("select v.questionId from AnswerVoteMapEntity v where v.answerId = :answerId")
    Long findQuestionId(@Param("answerId") Long answerId);

    // 点赞的总数
    Long countAllByQuestionId(Long questionId);

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 增 加
     *
     * ---------------------------------------------------------------------------
     */

    // 回答点赞
    void save(AnswerVoteMapEntity answerVoteMapEntity);

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 删 除
     *
     * ---------------------------------------------------------------------------
     */

    // 取消点赞
    @Transactional
    int deleteByAnswerIdAndUid(Long answerId,String uid);
}

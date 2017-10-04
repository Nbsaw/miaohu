package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.domain.AnswerVoteMap;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;

public interface AnswerVoteMapRepository extends CrudRepository<AnswerVoteMap,Long> {
    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */

    // 查看是否点赞
    @Query("select count(a) > 0 from AnswerVoteMap a where a.answerId = :answerId and a.uid = :uid")
    boolean isVote(@Param("answerId") Long answerId , @Param("uid") String uid);

    // 查找questionId
    @Query("select v.questionId from AnswerVoteMap v where v.answerId = :answerId")
    Long findQuestionId(@Param("answerId") Long answerId);

    // 点赞的总数
    Long countAllByQuestionId(Long questionId);

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

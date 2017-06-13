package com.nbsaw.miaohu.question;

import com.nbsaw.miaohu.question.answer.AnswerVoteMapEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;

/**
 * Created by nbsaw on 2017/5/17.
 */
public interface AnswerVoteMapRepository extends Repository<AnswerVoteMapEntity,Long> {
    // 查看是否点赞
    @Query("select count(a) > 0 from AnswerVoteMapEntity a where a.answerId = :answerId and a.uid = :uid")
    boolean isVote(@Param("answerId") Long answerId , @Param("uid") String uid);

    // 查找questionId
    @Query("select v.questionId from AnswerVoteMapEntity v where v.answerId = :answerId")
    Long findQuestionId(@Param("answerId") Long answerId);

    // 点赞的总数
    Long countAllByQuestionId(Long questionId);

    // 回答点赞
    void save(AnswerVoteMapEntity answerVoteMapEntity);

    // 取消点赞
    @Transactional
    int deleteByAnswerIdAndUid(Long answerId,String uid);
}

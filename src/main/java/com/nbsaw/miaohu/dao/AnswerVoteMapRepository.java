package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.model.AnswerVoteMap;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;

public interface AnswerVoteMapRepository extends CrudRepository<AnswerVoteMap,Long> {

    @Query("select count(a) > 0 from AnswerVoteMap a where a.answerId = :answerId and a.uid = :uid")
    boolean isVote(@Param("answerId") Long answerId , @Param("uid") String uid);

    @Query("select v.questionId from AnswerVoteMap v where v.answerId = :answerId")
    Long findQuestionId(@Param("answerId") Long answerId);

    Long countAllByQuestionId(Long questionId);

    @Transactional
    int deleteByAnswerIdAndUid(Long answerId,String uid);

}

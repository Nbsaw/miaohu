package com.miaohu.domain.quesstion.answerVoteMap;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;

/**
 * Created by nbsaw on 2017/5/17.
 */
public interface AnswerVoteMapRepository extends Repository<AnswerVoteMapEntity,Long> {
    // 查看是否点赞

    // 查找questionId

    // 点赞的总数
    Long countAllByQuestionId(Long questionId);

    // 回答点赞
    void save(AnswerVoteMapEntity answerVoteMapEntity);

    // 取消点赞
    @Transactional
}

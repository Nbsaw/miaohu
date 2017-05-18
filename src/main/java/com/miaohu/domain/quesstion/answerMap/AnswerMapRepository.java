package com.miaohu.domain.quesstion.answerMap;

import com.miaohu.domain.quesstion.answer.AnswerEntity;
import org.springframework.data.repository.Repository;

/**
 * Created by nbsaw on 2017/5/17.
 */
public interface AnswerMapRepository extends Repository<AnswerEntity,Long> {
    // 问题点赞

}

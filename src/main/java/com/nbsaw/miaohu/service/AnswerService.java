package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.model.Answer;
import com.nbsaw.miaohu.exception.ValidParamException;

import java.util.List;

public interface AnswerService {

    /**
     * 根据时倒叙获取对应问题的前五条回答
     * @param questionId 问题id
     * @return 倒叙问题列表
     */
    List<Answer> findAllById(Long questionId);

    /**
     * 根据对应问题id回答问题
     * @param questionId 问题id
     * @param content 回答内容
     * @param uid 用户id
     */
    void save(Long questionId , String content , String uid) throws ValidParamException;

}

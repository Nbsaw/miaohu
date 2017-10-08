package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.dto.AnswerDTO;
import com.nbsaw.miaohu.dto.PageDTO;

import java.util.List;

public interface AnswerService {

    PageDTO<List<AnswerDTO>> findAllByQuestionId(Long questionId, int page);

    boolean exists(Long answerId);

    boolean isQuestionExists(Long questionId);

    boolean isReplied(Long questionId,Long uid);

    void save(Long questionId,Long uid,String content);

    boolean vote(Long answerId , Long uid);

    void delete(Long answerId);
}

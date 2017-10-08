package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.dto.PageDTO;
import com.nbsaw.miaohu.dto.QuestionDTO;
import com.nbsaw.miaohu.dto.QuestionDetailedDTO;

import java.util.List;

public interface QuestionService {

    boolean existsById(Long questionId);

    boolean existsByTitle(String title);

    void save(Long uid,String title,String content,String[] tags,boolean anonymous);

    // TODO 用户资料
    // TODO 话题只显示一个
    // TODO 查看问题是否匿名
    PageDTO findAllQuestion(int page);

    QuestionDetailedDTO findOne(Long questionId, Long uid);
//
//    boolean belong(Long questionId,Long uid);
//
//    void delete(Long questionId);
//
//    boolean setAnonymous(Long questionId,Long uid);
//
//    void update(Long questionId,Long uid,String title,String content,boolean anonymous);

}

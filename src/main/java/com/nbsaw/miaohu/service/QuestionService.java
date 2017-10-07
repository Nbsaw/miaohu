package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.vo.QuestionVo;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    boolean existsById(Long questionId);

    boolean existsByTitle(String title);

    void save(String uid,String title,String content,String[] tags);

    // TODO 用户资料
    // TODO 话题只显示一个
    // TODO 查看问题是否匿名
    List<QuestionVo> findAllQuestion(int page);

    Map findOne(Long questionId,String uid);

    boolean belong(Long questionId,String uid);

    void delete(Long questionId);

    boolean setAnonymous(Long questionId,String uid);

    void update(Long questionId,String uid,String title,String content,boolean anonymous);

}

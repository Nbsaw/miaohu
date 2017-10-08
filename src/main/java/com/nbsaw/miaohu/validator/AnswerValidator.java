package com.nbsaw.miaohu.validator;

import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.service.AnswerService;
import com.nbsaw.miaohu.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @_({@Autowired}))
public class AnswerValidator {

    private final AnswerService service;

    public ErrorsMap addValid(Long questionId , Long uid){
        ErrorsMap errorsMap = new ErrorsMap();
        if (!service.isQuestionExists(questionId))
             errorsMap.put("questionId","不存在与该id相关的问题");
        else if (service.isReplied(questionId,uid))
            errorsMap.put("answer","已经回答过这个问题了");
        return errorsMap;
    }

    public ErrorsMap voteValid(Long answerId){
        ErrorsMap errorsMap = new ErrorsMap();
        if (!service.exists(answerId))
            errorsMap.put("answerId","不存在与该id相关的回答");
        return errorsMap;
    }

    public ErrorsMap deleteValid(Long answerId , Long uid){
        ErrorsMap errorsMap = new ErrorsMap();
        if (service.exists(answerId))
            errorsMap.put("answerId","不存在与该id相关的回答");
        else if (service.isReplied(answerId,uid))
            errorsMap.put("authorization","你无法删除其他人的回答");
        return errorsMap;
    }
}

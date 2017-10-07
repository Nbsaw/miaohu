package com.nbsaw.miaohu.serviceImpl;

import com.nbsaw.miaohu.dao.AnswerRepository;
import com.nbsaw.miaohu.dao.AnswerVoteMapRepository;
import com.nbsaw.miaohu.exception.ValidParamException;
import com.nbsaw.miaohu.model.Answer;
import com.nbsaw.miaohu.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class AnswerServiceImpl implements AnswerService {

    private final AnswerVoteMapRepository answerVoteMapRepository;
    private final AnswerRepository answerRepository;

    @Override
    public List<Answer> findAllById(Long questionId) {
        PageRequest pageRequest = new PageRequest(0,5,new Sort(Sort.Direction.DESC,"date"));
        return answerRepository.findAllByQuestionId(questionId,pageRequest);
    }

    @Override
    public void save(Long questionId, String content, String uid) throws ValidParamException {
        // 检测id是否为空
        if (questionId == null) {
            throw new ValidParamException("帖子id不应该为空");
        }
        // 检测是不是已经回答过的问题
        else if (answerRepository.isExists(questionId, uid)) {
            throw new ValidParamException("不可以重复回答问题:(");
        }
        // 检验回复是否为空
        else if (content.trim().length() == 0) {
            throw new ValidParamException("评论不能为空");
        }
        // 条件通过
        else {
            Answer answer = new Answer();
            answer.setQuestionId(questionId);
            answer.setContent(content);
            answer.setUid(uid);
            answerRepository.save(answer);
        }
    }
}

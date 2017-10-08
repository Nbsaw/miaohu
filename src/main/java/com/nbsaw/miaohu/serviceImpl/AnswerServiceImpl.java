package com.nbsaw.miaohu.serviceImpl;

import com.nbsaw.miaohu.dao.repository.AnswerRepository;
import com.nbsaw.miaohu.dao.repository.AnswerVoteRepository;
import com.nbsaw.miaohu.dao.repository.QuestionRepository;
import com.nbsaw.miaohu.dao.repository.UserRepository;
import com.nbsaw.miaohu.dao.repository.model.Answer;
import com.nbsaw.miaohu.dao.repository.model.AnswerVote;
import com.nbsaw.miaohu.dto.AnswerDTO;
import com.nbsaw.miaohu.dto.PageDTO;
import com.nbsaw.miaohu.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerVoteRepository answerVoteRepository;

    @Override
    public PageDTO<List<AnswerDTO>> findAllByQuestionId(Long questionId, int page) {
        Page<Answer> list = answerRepository.findAll(
                new PageRequest(page,5, new Sort(Sort.Direction.DESC,"creationDate")));
        List<AnswerDTO> DTOs = new ArrayList<>();
        list.forEach(answer -> DTOs.add(new AnswerDTO(answer,answerVoteRepository.countAllByAnswer(answer))));
        return new PageDTO(list,DTOs);
    }

    @Override
    public boolean exists(Long answerId) {
        return answerRepository.exists(answerId);
    }

    @Override
    public boolean isQuestionExists(Long questionId) {
        return questionRepository.exists(questionId);
    }

    @Override
    public boolean isReplied(Long questionId, Long uid) {
        return answerRepository.existsByQuestion_IdAndUser_Id(questionId,uid);
    }

    @Override
    public void save(Long questionId, Long uid, String content) {
        Answer answer = new Answer();
        answer.setQuestion(questionRepository.findOne(questionId));
        answer.setUser(userRepository.findOne(uid));
        answer.setContent(content);
        answerRepository.save(answer);
    }

    @Override
    public boolean vote(Long answerId, Long uid) {
        if (answerVoteRepository.existsByAnswer_IdAndUser_Id(answerId,uid)){
            answerVoteRepository.deleteByAnswer_IdAndUser_Id(answerId,uid);
            return false;
        }
        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(answerRepository.findOne(answerId));
        answerVote.setUser(userRepository.findOne(uid));
        answerVoteRepository.save(answerVote);
        return true;
    }

    @Override
    public void delete(Long answerId) {
        answerRepository.delete(answerId);
    }

}

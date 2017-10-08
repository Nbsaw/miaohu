package com.nbsaw.miaohu.serviceImpl;

import com.nbsaw.miaohu.common.HtmlUtils;
import com.nbsaw.miaohu.dao.repository.*;
import com.nbsaw.miaohu.dao.repository.model.Answer;
import com.nbsaw.miaohu.dao.repository.model.Question;
import com.nbsaw.miaohu.dao.repository.model.QuestionVote;
import com.nbsaw.miaohu.dao.repository.model.Tag;
import com.nbsaw.miaohu.dto.PageDTO;
import com.nbsaw.miaohu.dto.QuestionDTO;
import com.nbsaw.miaohu.dto.QuestionDetailedDTO;
import com.nbsaw.miaohu.service.QuestionService;
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
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AnswerVoteRepository answerVoteRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final QuestionVoteRepository questionVoteRepository;

    @Override
    public boolean existsById(Long questionId) {
        return questionRepository.exists(questionId);
    }

    @Override
    public boolean existsByTitle(String title) {
        return questionRepository.existsByTitle(title);
    }

    @Override
    public void save(Long uid, String title, String content,String[] tags,boolean anonymous) {
        Question question = new Question();
        question.setUser(userRepository.findOne(uid));
        question.setTitle(HtmlUtils.getInnerText(title));
        question.setContent(HtmlUtils.getInnerText(content));
        question.setAnonymous(anonymous);
        List<Tag> tagList = new ArrayList<>();
        // 存储标签的关联
        for (String tagName : tags)
            tagList.add(tagRepository.findByName(tagName));
        question.setTags(tagList);
        questionRepository.save(question);
    }

    @Override
    public PageDTO findAllQuestion(int page) {
        Page<Question> list = questionRepository.findAll(new PageRequest(page,10,
                new Sort(Sort.Direction.DESC,"creationDate")));
        List<QuestionDTO> DTOs = new ArrayList<>();
        list.forEach(question -> DTOs.add(new QuestionDTO(question,questionVoteRepository.countAllByQuestion(question))));
        return new PageDTO(list,DTOs);
    }

    @Override
    public QuestionDetailedDTO findOne(Long questionId, Long uid) {
        Question question = questionRepository.findOne(questionId);
        Long vote = questionVoteRepository.countAllByQuestion(question);
        boolean replied = answerRepository.existsByQuestion_IdAndUser_Id(questionId,uid);
        List<Answer> answers = answerRepository.findAllByQuestion_Id(questionId);
        List<Long> answersVote = new ArrayList<>();
        answers.forEach(answer -> answersVote.add(answerVoteRepository.countAllByAnswer(answer)));
        return new QuestionDetailedDTO(question,vote,replied,answers,answersVote);
    }
//
//    @Override
//    public boolean belong(Long questionId , Long uid) {
//        return questionRepository.existsByIdAndUid(questionId,uid);
//    }
//
//    @Override
//    public void delete(Long questionId) {
//        questionRepository.delete(questionId);
//    }
//
//    @Override
//    public boolean setAnonymous(Long questionId,Long uid){
//        boolean isAnonymous = questionRepository.isAnonymous(questionId);
//        questionRepository.setAnonymous(questionId, uid,!isAnonymous);
//        boolean isAnswerExists = answerRepository.existsByQuestionIdAndUid(questionId,uid);
//        if (isAnswerExists)
//            answerRepository.setAnonymous(questionId, uid,!isAnonymous);
//        return isAnonymous;
//    }
//
//    @Override
//    public void update(Long questionId, Long uid, String title, String content,boolean anonymous) {
//        questionRepository.updateContentByIdAndUid(questionId, uid, title, content,anonymous);
//    }

}

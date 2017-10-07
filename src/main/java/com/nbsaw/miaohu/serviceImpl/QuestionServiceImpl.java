package com.nbsaw.miaohu.serviceImpl;

import com.nbsaw.miaohu.dao.AnswerRepository;
import com.nbsaw.miaohu.dao.QuestionRepository;
import com.nbsaw.miaohu.dao.TagMapRepository;
import com.nbsaw.miaohu.dao.TagRepository;
import com.nbsaw.miaohu.model.Question;
import com.nbsaw.miaohu.model.Tag;
import com.nbsaw.miaohu.model.TagMap;
import com.nbsaw.miaohu.service.QuestionService;
import com.nbsaw.miaohu.vo.QuestionVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final TagMapRepository tagMapRepository;
    private final TagRepository tagRepository;
    private final AnswerRepository answerRepository;

    @Override
    public boolean existsById(Long questionId) {
        return questionRepository.exists(questionId);
    }

    @Override
    public boolean existsByTitle(String title) {
        return questionRepository.existsByTitle(title);
    }

    @Override
    public void save(String uid, String title, String content,String[] tags) {
        Question question = new Question();
        question.setUid(uid);
        question.setTitle(title);
        question.setContent(content);
        questionRepository.save(question);
        for (String tagName : tags) {
            TagMap tagMap = new TagMap();
            tagMap.setCorrelation(question.getId());
            tagMap.setTagId(tagRepository.findByNameIgnoreCase(tagName).getId());
            tagMap.setType("question");
            tagMapRepository.save(tagMap);
        }
    }

    @Override
    public List<QuestionVo> findAllQuestion(int page) {
        Page<Question> list = questionRepository.findAll(new PageRequest(page,10,new Sort(Sort.Direction.DESC,"date")));
        List<QuestionVo> result = new ArrayList<>();
        list.forEach(s -> {
            // 查找问题
            List<TagMap> tagMapEntities = tagMapRepository.findAllByCorrelationAndType(s.getId(),"question");
            // 查找问题所属的标签
            List<Tag> tagList = new ArrayList<>();
            tagMapEntities.forEach(map -> tagList.add(tagRepository.findById(map.getCorrelation())));
            // TODO 查找问题相关的用户
            result.add(new QuestionVo(s.getId(),s.getTitle(),s.getContent(),s.getDate(),tagList));
        });
        return result;
    }

    @Override
    public Map findOne(Long questionId,String uid) {
        // 根据问题id查找问题
        Question question = questionRepository.findOne(questionId);
        QuestionVo questionVo = new QuestionVo();

        // 获取各个可以暴露出去的字段
        questionVo.setId(question.getId());
        questionVo.setTitle(question.getTitle());
        questionVo.setTitle(question.getContent());
        questionVo.setDate(question.getDate());

        // 查找问题的标签映射
        List<TagMap> tagMapEntities = tagMapRepository.findAllByCorrelationAndType(questionId,"question");
        List tagList = new ArrayList();

        // 查找问题所属的标签
        tagMapEntities.forEach(map -> tagList.add(tagRepository.findById(map.getTagId())));
        Map result = new LinkedHashMap();
        result.put("question",questionVo);
        result.put("tag",tagList);

        // 判断是否回复过问题
        result.put("answer",answerRepository.isExists(questionId,uid));
        return result;
    }

    @Override
    public boolean belong(Long questionId, String uid) {
        return questionRepository.existsByIdAndUid(questionId,uid);
    }

    @Override
    public void delete(Long questionId) {
        questionRepository.delete(questionId);
    }

    @Override
    public boolean setAnonymous(Long questionId,String uid){
        boolean isAnonymous = questionRepository.isAnonymous(questionId);
        questionRepository.setAnonymous(questionId, uid,!isAnonymous);
        boolean isAnswerExists = answerRepository.isExists(questionId,uid);
        if (isAnswerExists)
            answerRepository.setAnonymous(questionId, uid,!isAnonymous);
        return isAnonymous;
    }

    @Override
    public void update(Long questionId, String uid, String title, String content,boolean anonymous) {
        questionRepository.updateContentByIdAndUid(questionId, uid, title, content,anonymous);
    }

}

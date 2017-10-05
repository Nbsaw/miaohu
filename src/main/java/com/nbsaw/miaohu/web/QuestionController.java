package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.common.JsonResult;
import com.nbsaw.miaohu.dao.AnswerRepository;
import com.nbsaw.miaohu.dao.QuestionRepository;
import com.nbsaw.miaohu.dao.TagMapRepository;
import com.nbsaw.miaohu.dao.TagRepository;
import com.nbsaw.miaohu.model.Question;
import com.nbsaw.miaohu.model.Tag;
import com.nbsaw.miaohu.model.TagMap;
import com.nbsaw.miaohu.common.JwtUtils;
import com.nbsaw.miaohu.service.QuestionService;
import com.nbsaw.miaohu.validator.QuestionValidator;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.QuestionVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired private QuestionRepository      questionRepository;
    @Autowired private TagRepository           tagRepository;
    @Autowired private TagMapRepository        tagMapRepository;
    @Autowired private AnswerRepository        answerRepository;
    @Autowired private JwtUtils jwtUtils;

    @Autowired QuestionValidator questionValidator;
    @Autowired QuestionService questionService;

    // 验证问题标题是否合法
    @PostMapping("/valid")
    public JsonResult validTitle(@RequestParam String title) {
        ErrorsMap errorsMap = questionValidator.titleValid(title);
        if (errorsMap.hasError()){
            return new JsonResult(400,"并不可以发布这个问题",errorsMap);
        }else{
            return new JsonResult(0,"这是一个可以发布的问题");
        }
    }

    // 发布一个新的问题
    @PostMapping("/post")
    public JsonResult post(@RequestParam String title,
                           @RequestParam String content,
                           @RequestParam String[] tags,
                           @RequestHeader("token") String token) {
        String uid = jwtUtils.getUid(token);
        ErrorsMap errorsMap = questionValidator.postValid(title,content,tags);
        if (errorsMap.hasError()){
            return new JsonResult(400,"问题发布失败",errorsMap);
        }else{
            questionService.save(uid,title,content,tags);
            return new JsonResult(0,"问题发布成功");
        }
    }

    // 查询近期发布的问题
    // TODO 用户资料
    // TODO 话题只显示一个
    // TODO 查看问题是否匿名
    @GetMapping
    public ResponseEntity all(@RequestParam(defaultValue = "0") int page){
        // 时间倒叙查找
        Page<Question> list = questionRepository.findAll(new PageRequest(page,10,new Sort(Sort.Direction.DESC,"date")));
        List<QuestionVo> result = new ArrayList<>();
        // 重新封装数据
        list.forEach(s -> {
            // 查找问题
            List<TagMap> tagMapEntities = tagMapRepository.findAllByCorrelationAndType(s.getId(),"question");
            // 查找问题所属的标签
            List<Tag> tagList = new ArrayList<>();
            tagMapEntities.forEach(map -> tagList.add(tagRepository.findById(map.getCorrelation())));
            // TODO 查找问题相关的用户
            result.add(new QuestionVo(s.getId(),s.getTitle(),s.getContent(),s.getDate(),tagList));
        });
//        return data(result);
        return null;
    }

    // 根据传过来的问题id获取对应的问题
    @GetMapping("/{questionId}")
    public GenericVo getId(@PathVariable Long questionId, @RequestHeader("token") String token) {
        // 判断问题是否存在
        if (! questionRepository.exists(questionId)){
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(400);
            messageVo.setMessage("问题不存在");
            return messageVo;
        }

        // 获取uid
        String uid = jwtUtils.getUid(token);

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

        // 封装结果
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(result);
        return resultVo;
    }

    // 根据传过来的问题id删除对应的问题
    @DeleteMapping("/delete/{questionId}")
    public MessageVo delete(@PathVariable Long questionId, @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtils.getUid(token);
        MessageVo messageVo = new MessageVo();
        // 判断问题是否存在
        if (! questionRepository.exists(questionId)){
            messageVo.setCode(400);
            messageVo.setMessage("问题不存在");
        }
        // 判断问题是否属于用户
        else if (! questionRepository.belong(questionId,uid)){
            messageVo.setCode(400);
            messageVo.setMessage("无法删除不属于你的问题");
        }
        else{
            questionRepository.deleteById(questionId);
            messageVo.setCode(200);
            messageVo.setMessage("删除问题成功");
        }
        return messageVo;
    }

     // TODO 加个问题修改理由表
     // TODO 加上是否匿名的字段
     // TODO 加上标签字段
     // TODO 问题修改历史
    // 根据id以及传过来的标题,内容修改对应的问题
    @PostMapping("/modify")
    public MessageVo modify(@RequestParam Long questionId,
                            @RequestParam String title,
                            @RequestParam(defaultValue = "") String content,
                            @RequestParam boolean anonymous,
                            @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtils.getUid(token);

        // 结果设置
        MessageVo result = new MessageVo();
        if (questionRepository.updateContentByIdAndUid(questionId, uid, title, content) == 1){
            result.setCode(200);
            result.setMessage("问题修改成功");
        }
        else{
            result.setCode(400);
            result.setMessage("问题修改失败");
        }
        return result;
    }

    // 设置(问题,回答)都为匿名 / 取消匿名
    @PostMapping("/anonymous")
    public MessageVo setAnonymous(@RequestParam Long questionId,
                                  @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtils.getUid(token);

        MessageVo result = new MessageVo();
        boolean status = false;
        // 首先判断一下问题存不存在
        boolean isExists = questionRepository.isExists(questionId);
        if (isExists) {
            // 判断问题是否是匿名状态
            boolean isAnonymous = questionRepository.isAnonymous(questionId);
            if (isAnonymous) {
                questionRepository.setAnonymousFalse(questionId, uid);
                status = true;
            } else {
                questionRepository.setAnonymousTrue(questionId, uid);
                result.setCode(200);
                result.setMessage("已经设为匿名!");
                status = false;
            }
        }else{
            result.setCode(400);
            result.setMessage("问题不存在或者没有权限修改");
        }

        // 判断用户是否回答过问题
        boolean isAnswerExists = answerRepository.isExists(questionId,uid);
        // 判断问题是否为匿名
        if (isAnswerExists){
            boolean isAnswerAnonymous = answerRepository.isAnonymous(questionId,uid);
            if (isAnswerAnonymous){
                answerRepository.setAnonymousFalse(questionId,uid);
                status = true;
            }else {
                answerRepository.setAnonymousTrue(questionId,uid);
                status = false;
            }
        }
        // 判断状态返回结果
        if (status){
            result.setCode(200);
            result.setMessage("已经取消匿名!");
        }
        else{
            result.setCode(200);
            result.setMessage("已经设置为匿名!");
        }
        return result;
    }
}


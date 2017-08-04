package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.entity.*;
import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.vo.QuestionVo;
import com.nbsaw.miaohu.repository.QuestionRepository;
import com.nbsaw.miaohu.repository.TagRepository;
import com.nbsaw.miaohu.repository.AnswerRepository;
import com.nbsaw.miaohu.repository.AnswerVoteMapRepository;
import com.nbsaw.miaohu.repository.TagMapRepository;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/question")
class QuestionController {

    @Autowired private QuestionRepository      questionRepository;
    @Autowired private TagRepository           tagRepository;
    @Autowired private TagMapRepository        tagMapRepository;
    @Autowired private AnswerRepository        answerRepository;
    @Autowired private JwtUtil                 jwtUtil;

    // 查询近期发布的问题
    // TODO 用户资料
    // TODO 话题只显示一个
    @GetMapping
    public ResultVo all(@RequestParam(value = "page",defaultValue = "0") int page, HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);

        // TODO 查看问题是否匿名
        List<QuestionEntity> list = questionRepository.findAll(new PageRequest(page,10));
        List<QuestionVo> result = new ArrayList<>();
        // 重新封装数据
        list.forEach(s -> {
            QuestionVo vo = new QuestionVo();
            vo.setId(s.getId());
            vo.setTitle(s.getTitle());
            vo.setContent(s.getContent());
            vo.setDate(s.getDate());
            // 查找问题
            List<TagMapEntity> tagMapEntities = tagMapRepository.findAllByCorrelationAndType(vo.getId(),"question");
            // 查找问题所属的标签
            List<TagEntity> tagList = new ArrayList<>();
            tagMapEntities.forEach(map -> tagList.add(tagRepository.findById(map.getCorrelation())));
            vo.setTag(tagList);
            // 查找问题相关的用户

            result.add(vo);
        });
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(result);
        return resultVo;
    }

    // 根据传过来的问题id获取对应的问题
    @GetMapping(value = "/{id}")
    public GenericVo getId(@PathVariable("id") Long id,HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 判断问题是否存在
        if (! questionRepository.exists(id)){
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(400);
            messageVo.setMessage("问题不存在");
            return messageVo;
        }

        // 获取uid
        String uid = jwtUtil.getUid(request);

        // 根据问题id查找问题
        QuestionEntity questionEntity = questionRepository.findOne(id);
        QuestionVo questionVo = new QuestionVo();

        // 获取各个可以暴露出去的字段
        questionVo.setId(questionEntity.getId());
        questionVo.setTitle(questionEntity.getTitle());
        questionVo.setTitle(questionEntity.getContent());
        questionVo.setDate(questionEntity.getDate());

        // 查找问题的标签映射
        List<TagMapEntity> tagMapEntities = tagMapRepository.findAllByCorrelationAndType(id,"question");
        List tagList = new ArrayList();

        // 查找问题所属的标签
        tagMapEntities.forEach(map -> tagList.add(tagRepository.findById(map.getTagId())));
        Map result = new LinkedHashMap();
        result.put("question",questionVo);
        result.put("tag",tagList);

        // 判断是否回复过问题
        result.put("answer",answerRepository.isExists(id,uid));

        // 封装结果
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(result);
        return resultVo;
    }

    // 验证问题标题是否合法
    @PostMapping(value = "/valid")
    public MessageVo validTitle(@RequestParam("title") String title) {
        MessageVo result = new MessageVo();
        title = title.trim();
        String last = title.substring(title.length() - 1); // 最后一个字符
        result.setCode(400); // 错误的比例高，所以直接先写上了
        // 判断标题是否为空
        if (title.equals("")) {
            result.setMessage("标题不能为空");
        }
        // 51个字的标题限制
        else if (title.length() > 51) {
            result.setMessage("标题太长");
        }
        // 末尾问号判断
        else if (last.equals("?") || last.equals("？")) {
            boolean isExists = questionRepository.existsQuestion(title);
            if (isExists)
                result.setMessage("已经存在的问题");
            else{
                result.setCode(200);
                result.setMessage("可以创建的问题");
            }
        }
        else {
            result.setMessage("你还没有给问题添加问号");
        }
        return result;
    }

    // 根据传过来的问题id删除对应的问题
    @DeleteMapping(value = "/delete/{id}")
    public MessageVo delete(@PathVariable(value = "id") Long id,HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);
        MessageVo messageVo = new MessageVo();
        // 判断问题是否存在
        if (! questionRepository.exists(id)){
            messageVo.setCode(400);
            messageVo.setMessage("问题不存在");
        }
        // 判断问题是否属于用户
        else if (! questionRepository.belong(id,uid)){
            messageVo.setCode(400);
            messageVo.setMessage("无法删除不属于你的问题");
        }
        else{
            questionRepository.deleteById(id);
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
    @PostMapping(value = "/modify")
    public MessageVo modify(@RequestParam(value = "id") Long id,
                            @RequestParam(value = "title") String title,
                            @RequestParam(value = "content", defaultValue = "") String content,
                            @RequestParam(value = "anonymous",defaultValue = "false") boolean anonymous,
                            HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);

        // 结果设置
        MessageVo result = new MessageVo();
        if (questionRepository.updateContentByIdAndUid(id, uid, title, content) == 1){
            result.setCode(200);
            result.setMessage("问题修改成功");
        }
        else{
            result.setCode(400);
            result.setMessage("问题修改失败");
        }
        return result;
    }

    // 发布一个新的问题
    @PostMapping(value = "/post")
    public MessageVo post(@RequestParam(value = "title") String title,
                       @RequestParam(value = "content") String content,
                       @RequestParam(value = "tags") String[] tags,
                          HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);

        QuestionEntity questionEntity = new QuestionEntity();
        boolean isExists = questionRepository.existsQuestion(title);
        title = title.trim();
        String last = title.substring(title.length() - 1); // 最后一个字符

        // 结果设置
        MessageVo messageVo = new MessageVo();
        messageVo.setCode(400);

        // 判断标题是否为空
        if (title.equals("")) {
            messageVo.setMessage("标题不能为空");
        }
        // 字符不能小于3个
        else if (title.length() < 3){
            messageVo.setMessage("问题字数太少了吧");
        }
        // 51个字的标题限制
        else if (title.length() > 51) {
            messageVo.setMessage("标题太长");
        }
        // 末尾问号判断
        else if (!last.equals("?") && !last.equals("？")) {
            messageVo.setMessage("你还没有给问题添加问号");
        }
        // 判断问题是否已存在
        else if(isExists){
            messageVo.setMessage("已经存在的问题");
        }
        // 尝试保存问题
        else {
            // 判断传过来的标签是否合法
            for (String tagName : tags) {
                if (!tagRepository.existsName(tagName)){
                    messageVo.setMessage(tagName + "是无效的标签");
                    return messageVo;
                }
            }
            // 保存问题
            // NOTE 关于这个问题什么放在前面,
            // 因为如果不放在前面getId会获取不到值 ...
            questionEntity.setUid(uid);
            questionEntity.setTitle(title);
            questionEntity.setContent(content);
            questionRepository.save(questionEntity);
            // 标签都合法保存下来
            for (String tagName : tags) {
                TagMapEntity tagMapEntity = new TagMapEntity();
                tagMapEntity.setCorrelation(questionEntity.getId());
                tagMapEntity.setTagId(tagRepository.findByNameIgnoreCase(tagName).getId());
                tagMapEntity.setType("question");
                tagMapRepository.save(tagMapEntity);
            }
            messageVo.setCode(200);
            messageVo.setMessage("问题发布成功");
       }
        return messageVo;
    }

    // 设置(问题,回答)都为匿名 / 取消匿名
    @PostMapping(value = "/anonymous")
    public MessageVo setAnonymous(
            @RequestParam(value = "questionId") Long questionId,
            HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);

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
            boolean isnswerAnonymous = answerRepository.isAnonymous(questionId,uid);
            if (isnswerAnonymous){
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


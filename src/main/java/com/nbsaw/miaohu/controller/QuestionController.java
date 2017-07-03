package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.entity.QuestionEntity;
import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.repository.QuestionRepository;
import com.nbsaw.miaohu.entity.TagMapEntity;
import com.nbsaw.miaohu.repository.TagRepository;
import com.nbsaw.miaohu.model.QuestionModel;
import com.nbsaw.miaohu.entity.AnswerEntity;
import com.nbsaw.miaohu.repository.AnswerRepository;
import com.nbsaw.miaohu.entity.AnswerVoteMapEntity;
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
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired private QuestionRepository questionRepository;
    @Autowired private TagRepository tagRepository;
    @Autowired private TagMapRepository tagMapRepository;
    @Autowired private AnswerRepository answerRepository;
    @Autowired private AnswerVoteMapRepository answerVoteMapRepository;
    @Autowired private JwtUtil jwtUtil;

    // 查询近期发布的问题
    @GetMapping
    public ResultVo all(@RequestParam(value = "page",defaultValue = "0") int page, HttpSession session) {
        String uid = (String) session.getAttribute("id");
        // TODO 查看问题是否匿名
        List<QuestionEntity> list = questionRepository.findAll(new PageRequest(page,10));
        List<QuestionModel> result = new ArrayList<QuestionModel>();
        // 重新封装数据
        list.stream().forEach(s -> {
            QuestionModel vo = new QuestionModel();
            vo.setId(s.getId());
            vo.setTitle(s.getTitle());
            vo.setTitle(s.getContent());
            vo.setDate(s.getDate());
            List<TagMapEntity> tagMapEntities = tagMapRepository.findAllByTagIdAndType(vo.getId(),"question");
            List tagList = new ArrayList();
            // 查找问题所属的标签
            tagMapEntities.stream().forEach(map -> {
                tagList.add(tagRepository.findById(map.getCorrelation()));
            });
            vo.setTag(tagList);
            result.add(vo);
        });
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(result);
        return resultVo;
    }

    // TODO 对应Id的问题是否存在的判断
    // 根据传过来的id获取对应的问题
    @GetMapping(value = "/{id}")
    public GenericVo getId(@PathVariable("id") Long id,HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 解析token
        String token = request.getHeader("token");
        System.out.println(token);
        String uid = (String) jwtUtil.parse(token).get("uid");

        // 根据问题id查找问题
        QuestionEntity s = questionRepository.findById(id);
        QuestionModel questionModel = new QuestionModel();

        // 获取各个可以暴露出去的字段
        questionModel.setId(s.getId());
        questionModel.setTitle(s.getTitle());
        questionModel.setTitle(s.getContent());
        questionModel.setDate(s.getDate());

        // 查找问题的标签映射
        List<TagMapEntity> tagMapEntities = tagMapRepository.findAllByTagIdAndType(id,"question");
        List tagList = new ArrayList();

        // 查找问题所属的标签
        tagMapEntities.stream().forEach(map -> {
            tagList.add(tagRepository.findById(map.getCorrelation()));
        });
        Map result = new LinkedHashMap();
        result.put("question",questionModel);
        result.put("tag",tagList);

        // 判断是否回复过问题
        result.put("answer",answerRepository.isExists(id,uid));

        // 封装结果
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(result);
        return resultVo;
    }

    // 验证标题是否合法
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
        else if (!last.equals("?") && !last.equals("？")) {
            result.setMessage("你还没有给问题添加问号");
        } else {
            boolean isExists = questionRepository.existsQuestion(title);
            if (isExists)
                result.setMessage("已经存在的问题");
            else{
                result.setCode(200);
                result.setMessage("可以创建的问题");
            }
        }
        return result;
    }

    // 根据传过来的id删除对应的问题
    @DeleteMapping(value = "/delete/{id}")
    public MessageVo delete(@PathVariable(value = "id") Long id, HttpSession session) {
        String uid = (String) session.getAttribute("id");
        MessageVo result = new MessageVo();
        if (questionRepository.deleteById(id) == 1){
            result.setCode(200);
            result.setMessage("删除问题成功");
        }
        else{
            result.setCode(40);
            result.setMessage("删除问题失败");
        }
        return result;
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
        // 解析token
        String token = request.getHeader("token");
        System.out.println(token);
        String uid = (String) jwtUtil.parse(token).get("uid");

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
                       @RequestParam(value = "tags") Long[] tags,
                          HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 解析token
        String token = request.getHeader("token");
        System.out.println(token);
        String uid = (String) jwtUtil.parse(token).get("uid");

        QuestionEntity questionEntity = new QuestionEntity();
        boolean isExists = questionRepository.existsQuestion(title);
        title = title.trim();
        String last = title.substring(title.length() - 1); // 最后一个字符

        // 结果设置
        MessageVo result = new MessageVo();
        result.setCode(400);

        // 判断标题是否为空
        if (title.equals("")) {
            result.setMessage("标题不能为空");
        }
        // 51个字的标题限制
        else if (title.length() > 51) {
            result.setMessage("标题太长");
        }
        // 末尾问号判断
        else if (!last.equals("?") && !last.equals("？")) {
            result.setMessage("你还没有给问题添加问号");
        }
        // 判断问题是否已存在
        else if(isExists){
            result.setMessage("已经存在的问题");
        }
        // 尝试创建
        else {
            // 判断传过来的标签是否合法
            for (long s : tags) {
                if (!tagRepository.exists(s))
                    result.setMessage("无效的标签");
                return result;
            }
            // 保存问题
            // NOTE 关于这个问题什么放在前面,
            // 因为如果不放在前面getId会获取不到值 ...
            questionEntity.setUid(uid);
            questionEntity.setTitle(title);
            questionEntity.setContent(content);
            questionRepository.save(questionEntity);
            // 标签都合法保存下来
            for (long s : tags) {
                TagMapEntity tagMapEntity = new TagMapEntity();
                tagMapEntity.setCorrelation(questionEntity.getId());
                tagMapEntity.setTagId(s);
                tagMapEntity.setType("question");
                tagMapRepository.save(tagMapEntity);
            }
            result.setCode(200);
            result.setMessage("问题发布成功");
       }
        return result;
    }

    // 查找问题的评论
    @GetMapping(value = "/answer/{id}")
    public ResultVo selectAnswerById(@PathVariable("id") Long id) {
        List<AnswerEntity> list = answerRepository.findAllByQuestionId(id,new PageRequest(0,5));
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(list);
        return resultVo;
    }

    // 回答问题
    @PostMapping(value = "/answer/add")
    public MessageVo answer(@RequestParam(value = "questionId") Long questionId,
                            @RequestParam(value = "content") String content,
                            HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 解析token
        String token = request.getHeader("token");
        System.out.println(token);
        String uid = (String) jwtUtil.parse(token).get("uid");

        MessageVo result = new MessageVo();

        // 检测id是否为空
        if (questionId == null) {
            result.setCode(403);
            result.setMessage("帖子id不应该为空");
        }
        // 检测是不是已经回答过的问题
        else if (answerRepository.isExists(questionId, uid)) {
            result.setCode(403);
            result.setMessage("不可以重复回答问题:(");
        }
        // 检验回复是否为空
        else if (content.trim().length() == 0) {
            result.setCode(400);
            result.setMessage("评论不能为空");
        }
        // 验证通过提交
        else {
            AnswerEntity answerEntity = new AnswerEntity();
            answerEntity.setQuestionId(questionId);
            answerEntity.setContent(content);
            answerEntity.setUid(uid);
            answerRepository.save(answerEntity);
            result.setCode(200);
            result.setMessage("评论成功");
        }
        return result;
    }

    // 回答删除接口
    @DeleteMapping(value = "/answer/delete")
    public MessageVo deleteAnswer(
            @RequestParam(value = "questionId") Long questionId,
            HttpServletRequest request) throws ExJwtException, InValidJwtException {

        // 解析token
        String token = request.getHeader("token");
        System.out.println(token);
        String uid = (String) jwtUtil.parse(token).get("uid");

        MessageVo result = new MessageVo();
        boolean isReply = answerRepository.isExists(questionId, uid);
       if (isReply){
           boolean isDeleted = answerRepository.isDeleted(questionId, uid);
           if (isDeleted) {
               result.setCode(400);
               result.setMessage("已经是删除状态了！");
           } else {
               answerRepository.setDeletedTrue(questionId, uid);
               result.setCode(200);
               result.setMessage("回答已删除");
           }
       }
       else{
           result.setCode(400);
           result.setMessage("无法删除没有回答的过问题");
       }
        return result;
    }

    // 撤销删除问题
    @PostMapping(value = "/answer/revoke")
    public MessageVo revokeAnswer(
            @RequestParam(value = "questionId") Long questionId,
            HttpServletRequest request) throws ExJwtException, InValidJwtException {

        // 解析token
        String token = request.getHeader("token");
        System.out.println(token);
        String uid = (String) jwtUtil.parse(token).get("uid");

        MessageVo result = new MessageVo();
        boolean isReply = answerRepository.isExists(questionId, uid);
        if (isReply) {
            boolean isDeleted = answerRepository.isDeleted(questionId, uid);
            if (isDeleted) {
                answerRepository.setDeletedFalse(questionId, uid);
                result.setCode(200);
                result.setMessage("撤销成功");
            } else {
                result.setCode(400);
                result.setMessage("已经是撤销状态了!");
            }
        }else{
            result.setCode(400);
            result.setMessage("没有回答过的问题无法撤销");
        }
        return result;
    }

    // 设置问题,回答为匿名 / 取消匿名
    @PostMapping(value = "/anonymous")
    public MessageVo setAnonymous(
            @RequestParam(value = "questionId") Long questionId,
            HttpServletRequest request) throws ExJwtException, InValidJwtException {

        // 解析token
        String token = request.getHeader("token");
        System.out.println(token);
        String uid = (String) jwtUtil.parse(token).get("uid");

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

    // TODO 推送点赞
    // 回答点赞
    @PostMapping(value = "/vote")
    public GenericVo vote(@RequestParam(value = "answerId") Long answerId,
                          HttpServletRequest request) throws ExJwtException, InValidJwtException {

        // 解析token
        String token = request.getHeader("token");
        System.out.println(token);
        String uid = (String) jwtUtil.parse(token).get("uid");

        Map map = new HashMap();
        // 通过回答的id找到问题的id
        Long questionId = answerVoteMapRepository.findQuestionId(answerId);
        // 在通过问题的id和用户id确认问题是不是点赞者自己的回答
        boolean isSelf = answerRepository.isSelf(questionId,uid);
        // 如果是自己的回答
        if (isSelf){
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(404);
            messageVo.setMessage("不能给自己点赞 XD");
            return messageVo;
        }
        else{
            // 判断是否点赞过了
            boolean isVote = answerVoteMapRepository.isVote(answerId,uid);
            if (!isVote){
                AnswerVoteMapEntity answerVoteMapEntity = new AnswerVoteMapEntity();
                answerVoteMapEntity.setUid(uid);
                answerVoteMapEntity.setAnswerId(answerId);
                answerVoteMapEntity.setQuestionId(questionId);
                answerVoteMapRepository.save(answerVoteMapEntity);
                map.put("vote",1);
            }
            else{
                answerVoteMapRepository.deleteByAnswerIdAndUid(answerId,uid);
                map.put("vote",0);
            }
            // 获取总数
            Long count = answerVoteMapRepository.countAllByQuestionId(questionId);
            map.put("count",count);
            ResultVo resultVo = new ResultVo();
            resultVo.setCode(200);
            resultVo.setResult(map);
            return resultVo;
        }
    }
}


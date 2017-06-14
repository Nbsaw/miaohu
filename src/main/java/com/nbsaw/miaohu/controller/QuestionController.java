package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.entity.QuestionEntity;
import com.nbsaw.miaohu.repository.QuestionRepository;
import com.nbsaw.miaohu.entity.TagMapEntity;
import com.nbsaw.miaohu.repository.TagRepository;
import com.nbsaw.miaohu.util.JsonUtil;
import com.nbsaw.miaohu.vo.QuestionVo;
import com.nbsaw.miaohu.entity.AnswerEntity;
import com.nbsaw.miaohu.repository.AnswerRepository;
import com.nbsaw.miaohu.entity.AnswerVoteMapEntity;
import com.nbsaw.miaohu.repository.AnswerVoteMapRepository;
import com.nbsaw.miaohu.repository.TagMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by fz on 17-3-31.
 */
@RestController
@RequestMapping(value = "/question")
public class QuestionController {
    // 问题
    @Autowired
    private QuestionRepository questionRepository;
    // 标签
    @Autowired
    private TagRepository tagRepository;
    // 标签映射
    @Autowired
    private TagMapRepository tagMapRepository;
    // 评论
    @Autowired
    private AnswerRepository answerRepository;
    // 回答点赞
    @Autowired
    private AnswerVoteMapRepository answerVoteMapRepository;

    /**
     * 查询近期的问题
     * @param session
     * @return 查询近期的问题
     */
    @GetMapping
    public String all(@RequestParam(value = "page",defaultValue = "0") int page,HttpSession session) {
        String uid = (String) session.getAttribute("id");
        // TODO 查看问题是否匿名
        List<QuestionEntity> list = questionRepository.findAll(new PageRequest(page,10));
        List<QuestionVo> result = new ArrayList<QuestionVo>();
        // 重新封装数据
        list.stream().forEach(s -> {
            QuestionVo vo = new QuestionVo();
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
        return JsonUtil.formatResult(200,"",result);
    }

    /**
     * 根据传过来的id获取某个问题
     *
     * @param id 文章的id
     * @return id对应的问题
     */
    @GetMapping(value = "/{id}")
    public String getId(@PathVariable("id") Long id,HttpSession session) {
        QuestionEntity s = questionRepository.findById(id);
        QuestionVo vo = new QuestionVo();
        vo.setId(s.getId());
        vo.setTitle(s.getTitle());
        vo.setTitle(s.getContent());
        vo.setDate(s.getDate());
        List<TagMapEntity> tagMapEntities = tagMapRepository.findAllByTagIdAndType(id,"question");
        List tagList = new ArrayList();
        // 查找问题所属的标签
        tagMapEntities.stream().forEach(map -> {
            tagList.add(tagRepository.findById(map.getCorrelation()));
        });
        Map result = new LinkedHashMap();
        result.put("question",vo);
        result.put("tag",tagList);
        // 判断是否回复过问题
        String uid = (String) session.getAttribute("id");
        result.put("answer",answerRepository.isExists(id,uid));
        return JsonUtil.formatResult(200,"",result);
    }

    /**
     * 验证标题是否合法
     * 有坑,问号不同算不同问题，知乎也是这样子
     * 所以我懒得改，当作是feature !!
     *
     * @param title
     * @return
     */
    @PostMapping(value = "/valid")
    public String validTitle(@RequestParam("title") String title) {
        String result = null;
        title = title.trim();
        String last = title.substring(title.length() - 1); // 最后一个字符
        // 判断标题是否为空
        if (title.equals("")) {
            result = JsonUtil.formatResult(400, "标题不能为空");
        }
        // 51个字的标题限制
        else if (title.length() > 51) {
            result = JsonUtil.formatResult(400, "标题太长");
        }
        // 末尾问号判断
        else if (!last.equals("?") && !last.equals("？")) {
            result = JsonUtil.formatResult(400, "你还没有给问题添加问号");
        } else {
            boolean isExists = questionRepository.existsQuestion(title);
            if (isExists)
                result = JsonUtil.formatResult(400, "已经存在的问题");
            else
                result = JsonUtil.formatResult(200, "可以创建的问题");
        }
        return result;
    }

    /**
     * 根据传过来的id删除某个问题
     *
     * @param id      问题的id
     * @param session
     * @return 返回状态, 问题删除成功或者失败
     */
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, HttpSession session) {
        String uid = (String) session.getAttribute("id");
        String result = null;
        if (questionRepository.deleteById(id) == 1)
            result = JsonUtil.formatResult(200, "删除问题成功");
        else
            result = JsonUtil.formatResult(400, "删除问题失败");
        return result;
    }

    /**
     * 根据id以及传过来的标题,内容修改
     * 对应的问题
     * 回答内容修改接口
     *
     * @param id      问题的id
     * @param title   问题的标题
     * @param content 问题的内容
     * @param session
     * @return 返回状态, 修改成功或者失败
     * TODO 加个问题修改理由表
     * TODO 加上是否匿名的字段
     * TODO 加上标签字段
     * TODO 问题修改历史
     */
    @PostMapping(value = "/modify")
    public String modify(@RequestParam(value = "id") Long id,
                         @RequestParam(value = "title") String title,
                         @RequestParam(value = "content", defaultValue = "") String content,
                         @RequestParam(value = "anonymous",defaultValue = "false") boolean anonymous,
                         HttpSession session) {
        String uid = (String) session.getAttribute("id");
        String result = null;
        if (questionRepository.updateContentByIdAndUid(id, uid, title, content) == 1)
            result = JsonUtil.formatResult(200, "问题修改成功");
        else
            result = JsonUtil.formatResult(400, "问题修改失败");
        return result;
    }

    /**
     * 发布一个新的问题,根据session
     * 里面查到的用户,先判断问题是否
     * 已经存在在数据库里面了,如果存在
     * 返回失败,不存在则发布
     *
     * @param title   问题的标题
     * @param content 问题的内容
     * @param session
     * @return 先判断问题是否已经存在在
     * 数据库里面了如果存在返回失败,不存
     * 在则发布
     */
    @PostMapping(value = "/post")
    public String post(@RequestParam(value = "title") String title,
                       @RequestParam(value = "content") String content,
                       @RequestParam(value = "tags") Long[] tags,
                       HttpSession session) {
        QuestionEntity questionEntity = new QuestionEntity();
        boolean isExists = questionRepository.existsQuestion(title);
        title = title.trim();
        String last = title.substring(title.length() - 1); // 最后一个字符
        String result = null;
        // 判断标题是否为空
        if (title.equals("")) {
            result = JsonUtil.formatResult(400, "标题不能为空");
        }
        // 51个字的标题限制
        else if (title.length() > 51) {
            result = JsonUtil.formatResult(400, "标题太长");
        }
        // 末尾问号判断
        else if (!last.equals("?") && !last.equals("？")) {
            result = JsonUtil.formatResult(400, "你还没有给问题添加问号");
        }
        // 判断问题是否已存在
        else if(isExists){
            result = JsonUtil.formatResult(400, "已经存在的问题");
        }
        // 尝试创建
        else {
            // 获取用户id
            String uid = (String) session.getAttribute("id");
            // 判断传过来的标签是否合法
            for (long s : tags)
                if (!tagRepository.exists(s))
                    return JsonUtil.formatResult(400, "无效的标签");
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
            result = JsonUtil.formatResult(200, "问题发布成功");
        }
        return result;
    }

    // 查找问题的评论
    @GetMapping(value = "/answer/{id}")
    public String selectAnswerById(@PathVariable("id") Long id) {
        List<AnswerEntity> list = answerRepository.findAllByQuestionId(id,new PageRequest(0,5));
        return JsonUtil.formatResult(200, "", list);
    }

    /**
     * 回答问题
     *
     * @param questionId
     * @param content
     * @return
     */
    @PostMapping(value = "/answer/add")
    public String answer(@RequestParam(value = "questionId") Long questionId,
                          @RequestParam(value = "content") String content,
                          HttpSession session) {
        String result = null;
        String uid = (String) session.getAttribute("id");
        // 检测id是否为空
        if (questionId == null) {
            result = JsonUtil.formatResult(403, "帖子id不应该为空");
        }
        // 检测是不是已经回答过的问题
        else if (answerRepository.isExists(questionId, uid)) {
            result = JsonUtil.formatResult(403, "不可以重复回答问题:(");
        }
        // 检验回复是否为空
        else if (content.trim().length() == 0) {
            result = JsonUtil.formatResult(400, "评论不能为空");
        }
        // 验证通过提交
        else {
            AnswerEntity answerEntity = new AnswerEntity();
            answerEntity.setQuestionId(questionId);
            answerEntity.setContent(content);
            answerEntity.setUid(uid);
            answerRepository.save(answerEntity);
            result = JsonUtil.formatResult(200, "评论成功");
        }
        return result;
    }

    // 回答删除接口
    @DeleteMapping(value = "/answer/delete")
    public String deleteAnswer(
            @RequestParam(value = "questionId") Long questionId
            , HttpSession session) {
        String result = null;
        String uid = (String) session.getAttribute("id");
        boolean isReply = answerRepository.isExists(questionId, uid);
       if (isReply){
           boolean isDeleted = answerRepository.isDeleted(questionId, uid);
           if (isDeleted) {
               result = JsonUtil.formatResult(400, "已经是删除状态了！");
           } else {
               answerRepository.setDeletedTrue(questionId, uid);
               result = JsonUtil.formatResult(200, "回答已删除");
           }
       }
       else{
           result = JsonUtil.formatResult(400, "没有回答的过问题无法删除");
       }
        return result;
    }

    // 撤销删除
    @PostMapping(value = "/answer/revoke")
    public String revokeAnswer(
            @RequestParam(value = "questionId") Long questionId,
            HttpSession session) {
        String result = null;
        String uid = (String) session.getAttribute("id");
        boolean isReply = answerRepository.isExists(questionId, uid);
        if (isReply) {
            boolean isDeleted = answerRepository.isDeleted(questionId, uid);
            if (isDeleted) {
                answerRepository.setDeletedFalse(questionId, uid);
                result = JsonUtil.formatResult(200, "撤销成功");
            } else {
                result = JsonUtil.formatResult(400, "已经是撤销状态了!");
            }
        }else{
            result = JsonUtil.formatResult(400, "没有回答过的问题无法撤销");
        }
        return result;
    }

    // 设置问题为匿名 / 取消匿名
    // 同时还要设置回答为匿名
    @PostMapping(value = "/anonymous")
    public String setAnonymous(
            @RequestParam(value = "questionId") Long questionId,
            HttpSession session){
        String result = null;
        String uid = (String) session.getAttribute("id");
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
                result = JsonUtil.formatResult(200, "已经设为匿名!");
                status = false;
            }
        }else{
            result = JsonUtil.formatResult(400, "问题不存在或者没有权限修改");
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
        if (status)
            result = JsonUtil.formatResult(200, "已经取消匿名!");
        else
            result = JsonUtil.formatResult(200, "已经设置为匿名!");
        return result;
    }

    // 回答点赞
    // TODO 推送点赞
    @PostMapping(value = "/vote")
    public String vote(@RequestParam(value = "answerId") Long answerId,HttpSession session){
        String result = null;
        String uid = (String) session.getAttribute("id");
        Map map = new HashMap();
        // 通过回答的id找到问题的id
        Long questionId = answerVoteMapRepository.findQuestionId(answerId);
        // 在通过问题的id和用户id确认问题是不是点赞者自己的回答
        boolean isSelf = answerRepository.isSelf(questionId,uid);
        // 如果是自己的回答
        if (isSelf){
            result = JsonUtil.formatResult(404,"不能给自己点赞 XD");
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
            result = JsonUtil.formatResult(200,"",map);
        }
        return result;
    }
}


package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.entity.AnswerEntity;
import com.nbsaw.miaohu.entity.AnswerVoteMapEntity;
import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.repository.AnswerRepository;
import com.nbsaw.miaohu.repository.AnswerVoteMapRepository;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/answer")
class AnswerController {
    @Autowired private AnswerVoteMapRepository answerVoteMapRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AnswerRepository answerRepository;

    // 查找问题的某个评论
    @GetMapping(value = "/{id}")
    public ResultVo selectAnswerById(@PathVariable("id") Long id) {
        // 暂时根据逆序查找
        List<AnswerEntity> list = answerRepository.findAllByQuestionId(id,new PageRequest(0,5,new Sort(Sort.Direction.DESC,"date")));
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(list);
        return resultVo;
    }

    // 回答问题
    @PostMapping(value = "/add")
    public MessageVo answer(@RequestParam(value = "questionId") Long questionId,
                            @RequestParam(value = "content") String content,
                            HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);

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

    // 回答删除
    @DeleteMapping(value = "/delete")
    public MessageVo deleteAnswer(
            @RequestParam(value = "questionId") Long questionId,
            HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);
        MessageVo result = new MessageVo();
        // 回答鉴权
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

    // 撤销删除回答
    @PostMapping(value = "/revoke")
    public MessageVo revokeAnswer(
            @RequestParam(value = "questionId") Long questionId,
            HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);

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

    // TODO 推送点赞
    // 回答点赞
    @PostMapping(value = "/vote")
    public GenericVo vote(@RequestParam(value = "answerId") Long answerId,
                          HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);

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

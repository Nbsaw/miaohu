package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.domain.Answer;
import com.nbsaw.miaohu.domain.AnswerVoteMap;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerVoteMapRepository answerVoteMapRepository;
    private final JwtUtil jwtUtil;
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerController(AnswerVoteMapRepository answerVoteMapRepository,
                            JwtUtil jwtUtil,
                            AnswerRepository answerRepository) {
        this.answerVoteMapRepository = answerVoteMapRepository;
        this.jwtUtil                 = jwtUtil;
        this.answerRepository        = answerRepository;
    }

    // 查找问题的某个评论
    @GetMapping("/{id}")
    public ResultVo selectAnswerById(@PathVariable Long id) {
        // 暂时根据逆序查找
        List<Answer> list = answerRepository.findAllByQuestionId(id,new PageRequest(0,5,new Sort(Sort.Direction.DESC,"date")));
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(list);
        return resultVo;
    }

    // 回答问题
    @PostMapping("/add")
    public MessageVo answer(@RequestParam Long questionId,
                            @RequestParam String content,
                            @RequestHeader String token) {
        // 获取uid
        String uid = jwtUtil.getUid(token);

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
            Answer answer = new Answer();
            answer.setQuestionId(questionId);
            answer.setContent(content);
            answer.setUid(uid);
            answerRepository.save(answer);
            result.setCode(200);
            result.setMessage("评论成功");
        }
        return result;
    }

    // 回答删除
    @DeleteMapping("/delete")
    public MessageVo deleteAnswer(@RequestParam Long questionId,
                                  @RequestHeader String token) {
        // 获取uid
        String uid = jwtUtil.getUid(token);
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
    @PostMapping("/revoke")
    public MessageVo revokeAnswer(@RequestParam Long questionId,
                                  @RequestHeader String token) {
        // 获取uid
        String uid = jwtUtil.getUid(token);

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
    @PostMapping("/vote")
    public GenericVo vote(@RequestParam Long answerId,
                          @RequestHeader String token) {
        // 获取uid
        String uid = jwtUtil.getUid(token);

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
                AnswerVoteMap answerVoteMap = new AnswerVoteMap();
                answerVoteMap.setUid(uid);
                answerVoteMap.setAnswerId(answerId);
                answerVoteMap.setQuestionId(questionId);
                answerVoteMapRepository.save(answerVoteMap);
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

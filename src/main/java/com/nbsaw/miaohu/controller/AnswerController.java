package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.domain.Answer;
import com.nbsaw.miaohu.domain.AnswerVoteMap;
import com.nbsaw.miaohu.dao.AnswerRepository;
import com.nbsaw.miaohu.dao.AnswerVoteMapRepository;
import com.nbsaw.miaohu.exception.ValidParamException;
import com.nbsaw.miaohu.service.AnswerService;
import com.nbsaw.miaohu.utils.JwtUtils;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired private AnswerVoteMapRepository answerVoteMapRepository;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private AnswerRepository answerRepository;
    @Autowired private AnswerService answerService;

    // 查找某个问题下的前5个回答
    @GetMapping("/{questionId}")
    public ResponseEntity selectAnswerById(@PathVariable Long questionId) {
        List<Answer> list = answerService.getAnswerList(questionId);
        return ResponseEntity.ok().body(list);
    }

    // 回答问题
    @PostMapping("/add")
    public ResponseEntity answer(@RequestParam Long questionId,
                            @RequestParam String content,
                            @RequestHeader String token) throws ValidParamException {
        String uid = jwtUtils.getUid(token);
        answerService.save(questionId,content,uid);
        return ResponseEntity.ok().body(uid);
    }

    // 回答删除
    @DeleteMapping("/delete")
    public MessageVo deleteAnswer(@RequestParam Long answerId,
                                  @RequestHeader String token) {
        // 获取uid
        String uid = jwtUtils.getUid(token);
        MessageVo result = new MessageVo();

        return result;
    }

    // 撤销删除回答
    @PostMapping("/revoke")
    public MessageVo revokeAnswer(@RequestParam Long answerId,
                                  @RequestHeader String token) {
        // 获取uid
        String uid = jwtUtils.getUid(token);

        MessageVo result = new MessageVo();
        boolean isReply = answerRepository.isExists(answerId, uid);
        if (isReply) {
            boolean isDeleted = answerRepository.isDeleted(answerId, uid);
            if (isDeleted) {
                answerRepository.setDeletedFalse(answerId, uid);
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
        String uid = jwtUtils.getUid(token);

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

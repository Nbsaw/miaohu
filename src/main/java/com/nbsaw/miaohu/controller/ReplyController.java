package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.repository.ReplyRepository;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public class ReplyController {
    @Autowired ReplyRepository replyRepository;

    // 回复文章
    @PostMapping(value = "/reply/add")
    public MessageVo answer(@RequestParam(value = "articleId") Long articleId,
                            @RequestParam(value = "content") String content,
                            HttpServletRequest request) {

        return null;
    }

    // 查找文章的评论
    @GetMapping(value = "/reply/{id}")
    public ResultVo selectAnswerById(@PathVariable("id") Long id) {
        return null;
    }

    // 回复删除
    @DeleteMapping(value = "/reply/delete")
    public MessageVo deleteAnswer(
            @RequestParam(value = "articleId") Long articleId,
            HttpServletRequest request) {
        return null;
    }

    // 回复点赞
    @PostMapping(value = "/reply/vote")
    public GenericVo voteReply(@RequestParam(value = "replyId") Long replyId,
                               HttpServletRequest request){
        return null;
    }
}

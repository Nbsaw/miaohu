package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.repository.ArticleRepository;
import com.nbsaw.miaohu.repository.ReplyRepository;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/reply")
class ReplyController {
    @Autowired ReplyRepository   replyRepository;
    @Autowired ArticleRepository articleRepository;
    // 回复文章
    @PostMapping(value = "/add")
    public MessageVo answer(@RequestParam(value = "articleId") Long articleId,
                            @RequestParam(value = "content") String content,
                            HttpServletRequest request) {
        // 判断文章的回复状态

        return null;
    }

    // 查找文章的评论
    @GetMapping(value = "/{id}")
    public ResultVo selectAnswerById(@PathVariable("id") Long id) {
        return null;
    }

    // 回复删除
    @DeleteMapping(value = "/delete")
    public MessageVo deleteAnswer(
            @RequestParam(value = "articleId") Long articleId,
            HttpServletRequest request) {
        return null;
    }

    // 回复点赞
    @PostMapping(value = "/vote")
    public GenericVo voteReply(@RequestParam(value = "replyId") Long replyId,
                               HttpServletRequest request){
        return null;
    }
}

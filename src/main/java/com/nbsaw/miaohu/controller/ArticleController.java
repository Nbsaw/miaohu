package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.repository.ArticleRepository;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired ArticleRepository articleRepository;

    // 根据传过来的文章id获取对应的文章
    @GetMapping(value = "/{id}")
    public GenericVo getId(@PathVariable("id") Long id, HttpServletRequest request) throws ExJwtException, InValidJwtException {
        return null;
    }

    // 验证文章标题是否合法
    @PostMapping(value = "/valid")
    public MessageVo validTitle(@RequestParam("title") String title) {
        return null;
    }

    // 根据传过来的问题id删除对应的问题
    @DeleteMapping(value = "/delete/{id}")
    public MessageVo delete(@PathVariable(value = "id") Long id, HttpSession session) {
        return null;
    }

    // 根据id以及传过来的标题,内容修改对应的文章
    @PostMapping(value = "/modify")
    public MessageVo modify(@RequestParam(value = "id") Long id,
                            @RequestParam(value = "title") String title,
                            @RequestParam(value = "content", defaultValue = "") String content,
                            @RequestParam(value = "anonymous", defaultValue = "false") boolean anonymous,
                            HttpServletRequest request)  {
        return null;
    }

    // 发布一个新的文章
    // TODO 文章回复状态
    @PostMapping(value = "/post")
    public MessageVo post(@RequestParam(value = "title") String title,
                          @RequestParam(value = "content") String content,
                          @RequestParam(value = "tags") String[] tags,
                          HttpServletRequest request) {

        return null;
    }

    // 查找文章的评论
    @GetMapping(value = "/answer/{id}")
    public ResultVo selectAnswerById(@PathVariable("id") Long id) {
        return null;
    }

    // 回复文章
    @PostMapping(value = "/reply/add")
    public MessageVo answer(@RequestParam(value = "articleId") Long articleId,
                            @RequestParam(value = "content") String content,
                            HttpServletRequest request) {
        return null;
    }

    // 回复删除
    @DeleteMapping(value = "/reply/delete")
    public MessageVo deleteAnswer(
            @RequestParam(value = "articleId") Long articleId,
            HttpServletRequest request) {
        return null;
    }

    // 撤销删除回复
    @PostMapping(value = "/reply/revoke")
    public MessageVo revokeAnswer(
            @RequestParam(value = "articleId") Long articleId,
            HttpServletRequest request) {
        return null;
    }

    // 文章点赞
    @PostMapping(value = "/vote")
    public GenericVo vote(@RequestParam(value = "replyId") Long replyId,
                          HttpServletRequest request){
        return null;
    }


    // 回复点赞
    @PostMapping(value = "/reply/vote")
    public GenericVo voteReply(@RequestParam(value = "replyId") Long replyId,
                          HttpServletRequest request){
        return null;
    }
}

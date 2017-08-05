package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.entity.ArticleEntity;
import com.nbsaw.miaohu.entity.ReplyEntity;
import com.nbsaw.miaohu.entity.ReplyVoteEntity;
import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.repository.ArticleRepository;
import com.nbsaw.miaohu.repository.ReplyRepository;
import com.nbsaw.miaohu.repository.ReplyVoteRepository;
import com.nbsaw.miaohu.type.ReplyStatusType;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/reply")
class ReplyController {
    @Autowired ReplyRepository     replyRepository;
    @Autowired ReplyVoteRepository replyVoteRepository;
    @Autowired ArticleRepository   articleRepository;
    @Autowired JwtUtil             jwtUtil;

    // 回复文章
    @PostMapping(value = "/add")
    public MessageVo reply(@RequestParam(value = "articleId") Long articleId,
                            @RequestParam(value = "content") String content,
                            HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);
        MessageVo messageVo = new MessageVo();
        if (!articleRepository.exists(articleId)){
            messageVo.setCode(404);
            messageVo.setMessage("不能回复不存在的文章");
        }
        else{
            // 检查文章的回复状态
            ArticleEntity   articleEntity  =  articleRepository.findOne(articleId);
            ReplyStatusType replyStatus    =  articleEntity.getReplyStatus();
            String escape = HtmlUtils.htmlEscape(content);
            System.out.println(escape);
            if(replyStatus == ReplyStatusType.NO){
                messageVo.setCode(400);
                messageVo.setMessage("该文章不允许回复");
                return messageVo;
            }
            ReplyEntity replyEntity = new ReplyEntity();
            replyEntity.setArticleId(articleId);
            replyEntity.setContent(escape);
            replyEntity.setUid(uid);
            if (replyStatus == ReplyStatusType.EXAMINE)
                replyEntity.setPass(false);
            else if(replyStatus == ReplyStatusType.YES)
                replyEntity.setPass(true);
            replyRepository.save(replyEntity);
            messageVo.setCode(200);
            messageVo.setMessage("回复成功");
        }
        return messageVo;
    }

    // 回复点赞
    @PostMapping(value = "/vote")
    public MessageVo voteReply(@RequestParam(value = "replyId") Long replyId,
                               HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);
        MessageVo messageVo = new MessageVo();
        if (!replyRepository.exists(replyId)){
            messageVo.setCode(404);
            messageVo.setMessage("回复不存在");
        }else{
            ReplyVoteEntity replyVoteEntity = replyVoteRepository.findByReplyIdAndUid(replyId,uid);
            if (replyVoteEntity == null){
                replyVoteEntity = new ReplyVoteEntity();
                replyVoteEntity.setReplyId(replyId);
                replyVoteEntity.setUid(uid);
                replyVoteRepository.save(replyVoteEntity);
                messageVo.setCode(200);
                messageVo.setMessage("回复点赞成功");
            }else{
                replyVoteRepository.delete(replyVoteEntity);
                messageVo.setCode(200);
                messageVo.setMessage("取消点赞成功");
            }
        }
        return messageVo;
    }

    // 回复删除
    // TODO 关联回复删除
    @DeleteMapping(value = "/delete")
    public MessageVo deleteAnswer(
            @RequestParam(value = "replyId") Long replyId,
            HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);
        MessageVo messageVo = new MessageVo();
        if (!replyRepository.exists(replyId)) {
            messageVo.setCode(404);
            messageVo.setMessage("回复不存在");
        }else if (!replyRepository.belong(replyId,uid)){
            messageVo.setCode(400);
            messageVo.setMessage("无法删除不属于你的回复");
        }else{
            replyRepository.delete(replyId);
            messageVo.setCode(200);
            messageVo.setMessage("删除成功");
        }
        return messageVo;
    }
}

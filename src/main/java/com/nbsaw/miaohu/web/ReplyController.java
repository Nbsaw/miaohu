package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.model.Article;
import com.nbsaw.miaohu.model.Reply;
import com.nbsaw.miaohu.model.ReplyVote;
import com.nbsaw.miaohu.dao.ArticleRepository;
import com.nbsaw.miaohu.dao.ReplyRepository;
import com.nbsaw.miaohu.dao.ReplyVoteRepository;
import com.nbsaw.miaohu.type.ReplyStatusType;
import com.nbsaw.miaohu.common.JwtUtils;
import com.nbsaw.miaohu.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired private ReplyRepository     replyRepository;
    @Autowired private ReplyVoteRepository replyVoteRepository;
    @Autowired private ArticleRepository   articleRepository;
    @Autowired private JwtUtils jwtUtils;

    // 回复文章
    // TODO 作者可以直接回复
    // TODO 推送
    @PostMapping("/add")
    public MessageVo reply(@RequestParam Long articleId,
                           @RequestParam String content,
                           @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtils.getUid(token);
        MessageVo messageVo = new MessageVo();
        if (!articleRepository.exists(articleId)){
            messageVo.setCode(404);
            messageVo.setMessage("不能回复不存在的文章");
        }
        else{
            // 检查文章的回复状态
            Article article  =  articleRepository.findOne(articleId);
            ReplyStatusType replyStatus    =  article.getReplyStatus();
            String escape = HtmlUtils.htmlEscape(content);
            System.out.println(escape);
            if(replyStatus == ReplyStatusType.NO){
                messageVo.setCode(400);
                messageVo.setMessage("该文章不允许回复");
                return messageVo;
            }
            Reply reply = new Reply();
            reply.setArticleId(articleId);
            reply.setContent(escape);
            reply.setUid(uid);
            if (replyStatus == ReplyStatusType.EXAMINE)
                reply.setPass(false);
            else if(replyStatus == ReplyStatusType.YES)
                reply.setPass(true);
            replyRepository.save(reply);
            messageVo.setCode(200);
            messageVo.setMessage("回复成功");
        }
        return messageVo;
    }

    // 回复点赞
    @PostMapping("/vote")
    public MessageVo voteReply(@RequestParam Long replyId,
                               @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtils.getUid(token);
        MessageVo messageVo = new MessageVo();
        if (!replyRepository.exists(replyId)){
            messageVo.setCode(404);
            messageVo.setMessage("回复不存在");
        }else{
            ReplyVote replyVote = replyVoteRepository.findByReplyIdAndUid(replyId,uid);
            if (replyVote == null){
                replyVote = new ReplyVote();
                replyVote.setReplyId(replyId);
                replyVote.setUid(uid);
                replyVoteRepository.save(replyVote);
                messageVo.setCode(200);
                messageVo.setMessage("回复点赞成功");
            }else{
                replyVoteRepository.delete(replyVote);
                messageVo.setCode(200);
                messageVo.setMessage("取消点赞成功");
            }
        }
        return messageVo;
    }

    // 回复删除
    // TODO 关联回复删除
    @DeleteMapping("/delete")
    public MessageVo deleteAnswer(@RequestParam Long replyId,
                                  @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtils.getUid(token);
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

    // TODO 回复审核
    // TODO 两个操作 公开和删除
    // 无论是公开还是删除都不推送
    // 判断是否为作者。判断回复是否属于文章。
    @PostMapping("/judge")
    public MessageVo judge(@RequestParam(value = "replyId") Long replyId,
                           @RequestParam(value = "action") String action,
                           @RequestHeader("token") String token) {
        String uid = jwtUtils.getUid(token);
        action = action.toLowerCase();
        Reply reply =  replyRepository.findOne(replyId);
        MessageVo messageVo = new MessageVo();
        // 判断回答是否存在
        if (reply == null){
            messageVo.setCode(404);
            messageVo.setMessage("回复不存在");
        }
        // 判断是否为自己文章下的回答
        else if (replyRepository.isAuthor(replyId,uid) == 0){
            messageVo.setCode(400);
            messageVo.setMessage("不能你的文章无法审核评论");
        }
        else{
            switch (action) {
                case "delete":
                    replyRepository.delete(replyId);
                    messageVo.setCode(200);
                    messageVo.setMessage("删除成功");
                    break;
                case "pass":
                    reply.setPass(true);
                    replyRepository.save(reply);
                    messageVo.setCode(200);
                    messageVo.setMessage("设置评论审核通过成功");
                    break;
                default:
                    messageVo.setCode(404);
                    messageVo.setMessage("不存在的操作");
                    break;
            }
        }
        return  messageVo;
    }
}

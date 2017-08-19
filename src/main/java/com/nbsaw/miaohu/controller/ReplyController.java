package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.entity.ArticleEntity;
import com.nbsaw.miaohu.entity.ReplyEntity;
import com.nbsaw.miaohu.entity.ReplyVoteEntity;
import com.nbsaw.miaohu.repository.ArticleRepository;
import com.nbsaw.miaohu.repository.ReplyRepository;
import com.nbsaw.miaohu.repository.ReplyVoteRepository;
import com.nbsaw.miaohu.type.ReplyStatusType;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired ReplyRepository     replyRepository;
    @Autowired ReplyVoteRepository replyVoteRepository;
    @Autowired ArticleRepository   articleRepository;
    @Autowired JwtUtil             jwtUtil;

    // 回复文章
    // TODO 作者可以直接回复
    // TODO 推送
    @PostMapping("/add")
    public MessageVo reply(@RequestParam Long articleId,
                           @RequestParam String content,
                           @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtil.getUid(token);
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
    @PostMapping("/vote")
    public MessageVo voteReply(@RequestParam Long replyId,
                               @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtil.getUid(token);
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
    @DeleteMapping("/delete")
    public MessageVo deleteAnswer(@RequestParam Long replyId,
                                  @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtil.getUid(token);
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
        String uid = jwtUtil.getUid(token);
        action = action.toLowerCase();
        ReplyEntity replyEntity =  replyRepository.findOne(replyId);
        MessageVo messageVo = new MessageVo();
        // 判断回答是否存在
        if (replyEntity == null){
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
                    replyEntity.setPass(true);
                    replyRepository.save(replyEntity);
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

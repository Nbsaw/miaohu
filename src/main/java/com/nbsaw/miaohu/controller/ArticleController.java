package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.entity.ArticleEntity;
import com.nbsaw.miaohu.entity.TagMapEntity;
import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.repository.ArticleRepository;
import com.nbsaw.miaohu.repository.TagMapRepository;
import com.nbsaw.miaohu.repository.TagRepository;
import com.nbsaw.miaohu.type.ReplyStatusType;
import com.nbsaw.miaohu.util.EnumUtil;
import com.nbsaw.miaohu.util.JwtUtil;
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
    @Autowired JwtUtil jwtUtil;
    @Autowired TagRepository tagRepository;
    @Autowired TagMapRepository tagMapRepository;

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


    // 发布一个新的文章
    @PostMapping(value = "/post")
    public MessageVo post(@RequestParam(value = "title") String title,
                          @RequestParam(value = "content") String content,
                          @RequestParam(value = "tags") String[] tags,
                          @RequestParam(value = "replyStatus") String replyStatus,
                          HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);

        // 结果设置
        MessageVo messageVo = new MessageVo();
        messageVo.setCode(400);

        // 判断标题是否为空
        if (title.equals("")) {
            messageVo.setMessage("标题不能为空");
        }
        // 字符不能小于3个
        else if (title.length() < 3){
            messageVo.setMessage("问题字数太少了吧");
        }
        // 51个字的标题限制
        else if (title.length() > 10) {
            messageVo.setMessage("标题超过10个字,无法保存");
        }
        else {
            // 判断传过来的标签是否合法
            for (String tagName : tags) {
                if (!tagRepository.existsName(tagName)) {
                    messageVo.setMessage(tagName + "是无效的标签");
                    return messageVo;
                }
            }
            // 回复权限检查
            if (!EnumUtil.equalsOf(ReplyStatusType.class,replyStatus)){
                messageVo.setMessage("无效的文章状态");
                return messageVo;
            }
            // 保存文章
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setUid(uid);
            articleEntity.setTitle(title);
            articleEntity.setContent(content);
            articleEntity.setReplyStatus(ReplyStatusType.valueOf(replyStatus));
            articleRepository.save(articleEntity);
            // 标签都合法保存下来
            for (String tagName : tags) {
                TagMapEntity tagMapEntity = new TagMapEntity();
                tagMapEntity.setCorrelation(articleEntity.getId());
                tagMapEntity.setTagId(tagRepository.findByName(tagName).getId());
                tagMapEntity.setType("article");
                tagMapRepository.save(tagMapEntity);
            }
            // 设置返回信息
            messageVo.setCode(200);
            messageVo.setMessage("文章发布成功");
        }
        return messageVo;
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

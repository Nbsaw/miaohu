package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.entity.ArticleEntity;
import com.nbsaw.miaohu.entity.QuestionEntity;
import com.nbsaw.miaohu.entity.TagMapEntity;
import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.repository.ArticleRepository;
import com.nbsaw.miaohu.repository.ReplyRepository;
import com.nbsaw.miaohu.repository.TagMapRepository;
import com.nbsaw.miaohu.repository.TagRepository;
import com.nbsaw.miaohu.type.ReplyStatusType;
import com.nbsaw.miaohu.util.EnumUtil;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired ArticleRepository articleRepository;
    @Autowired JwtUtil           jwtUtil;
    @Autowired TagRepository     tagRepository;
    @Autowired TagMapRepository  tagMapRepository;
    @Autowired ReplyRepository   replyRepository;

    // 根据传过来的文章id获取对应的文章
    @GetMapping(value = "/{id}")
    public GenericVo getId(@PathVariable("id") Long id, HttpServletRequest request) throws ExJwtException, InValidJwtException {

        // 判断问题是否存在
        if (! articleRepository.exists(id)){
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(400);
            messageVo.setMessage("文章不存在");
            return messageVo;
        }

        // 根据问题id查找问题
        ArticleEntity articleEntity = articleRepository.findOne(id);
        ArticleVo articleVo = new ArticleVo();

        // 获取各个可以暴露出去的字段
        articleVo.setId(articleEntity.getId());
        articleVo.setTitle(articleEntity.getTitle());
        articleVo.setTitle(articleEntity.getContent());
        articleVo.setDate(articleEntity.getDate());

        // 查找文章的标签映射
        List<TagMapEntity> tagMapEntities = tagMapRepository.findAllByCorrelationAndType(id,"article");
        List tagList = new ArrayList();

        // 查找问题所属的标签
        tagMapEntities.forEach(map -> tagList.add(tagRepository.findById(map.getTagId())));
        Map result = new LinkedHashMap();
        result.put("article",articleVo);
        result.put("tag",tagList);

        // 封装结果
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(result);
        return resultVo;
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
                tagMapEntity.setTagId(tagRepository.findByNameIgnoreCase(tagName).getId());
                tagMapEntity.setType("article");
                tagMapRepository.save(tagMapEntity);
            }
            // 设置返回信息
            messageVo.setCode(200);
            messageVo.setMessage("文章发布成功");
        }
        return messageVo;
    }

    // 回复文章
    @PostMapping(value = "/reply/add")
    public MessageVo answer(@RequestParam(value = "articleId") Long articleId,
                            @RequestParam(value = "content") String content,
                            HttpServletRequest request) {

        return null;
    }

    // 查找文章的评论
    @GetMapping(value = "/answer/{id}")
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

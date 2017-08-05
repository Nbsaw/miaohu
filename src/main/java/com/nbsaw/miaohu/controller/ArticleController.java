package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.entity.ArticleEntity;
import com.nbsaw.miaohu.entity.ArticleVoteEntity;
import com.nbsaw.miaohu.entity.TagMapEntity;
import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.repository.*;
import com.nbsaw.miaohu.type.ReplyStatusType;
import com.nbsaw.miaohu.util.EnumUtil;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/article")
class ArticleController {

    @Autowired ArticleRepository     articleRepository;
    @Autowired JwtUtil               jwtUtil;
    @Autowired TagRepository         tagRepository;
    @Autowired TagMapRepository      tagMapRepository;
    @Autowired ArticleVoteRepository articleVoteRepository;

    // TODO 全部文章查询接口

    // 根据传过来的文章id获取对应的文章
    @GetMapping(value = "/{id}")
    public GenericVo getId(@PathVariable("id") Long id, HttpServletRequest request){

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
    public MessageVo delete(@PathVariable(value = "id") Long id, HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);
        MessageVo messageVo = new MessageVo();
        // 判断问题是否存在
        if (! articleRepository.exists(id)){
            messageVo.setCode(400);
            messageVo.setMessage("文章不存在");
        }
        // 判断问题是否属于用户
        else if (! articleRepository.belong(id,uid)){
            messageVo.setCode(400);
            messageVo.setMessage("无法删除不属于你的文章");
        }
        else{
            articleRepository.delete(id);
            messageVo.setCode(200);
            messageVo.setMessage("删除成功");
        }
        return messageVo;
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

    // 文章点赞
    @PostMapping(value = "/vote")
    public GenericVo vote(@RequestParam(value = "id") Long id,
                          HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);
        MessageVo messageVo = new MessageVo();
        if (!articleRepository.exists(id)){
            messageVo.setCode(404);
            messageVo.setMessage("文章不存在");
        }
        // 如果已点赞
        else if (articleVoteRepository.isVoted(id,uid)){
            articleVoteRepository.deleteByArticleIdAndUid(id,uid);
            messageVo.setCode(200);
            messageVo.setMessage("已取消点赞");
        }
        // 如果没有点赞
        else{
            ArticleVoteEntity articleVoteEntity = new ArticleVoteEntity();
            articleVoteEntity.setArticleId(id);
            articleVoteEntity.setUid(uid);
            articleVoteRepository.save(articleVoteEntity);
            messageVo.setCode(200);
            messageVo.setMessage("点赞成功");
        }
        return messageVo;
    }
}

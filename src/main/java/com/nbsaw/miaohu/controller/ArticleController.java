package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.domain.Article;
import com.nbsaw.miaohu.domain.ArticleVote;
import com.nbsaw.miaohu.domain.TagMap;
import com.nbsaw.miaohu.repository.*;
import com.nbsaw.miaohu.type.ReplyStatusType;
import com.nbsaw.miaohu.util.EnumUtil;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleRepository     articleRepository;
    private final JwtUtil               jwtUtil;
    private final TagRepository         tagRepository;
    private final TagMapRepository      tagMapRepository;
    private final ArticleVoteRepository articleVoteRepository;
    private final ReplyRepository       replyRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository,
                             JwtUtil jwtUtil,
                             TagRepository tagRepository,
                             TagMapRepository tagMapRepository,
                             ArticleVoteRepository articleVoteRepository,
                             ReplyRepository replyRepository) {
        this.articleRepository      = articleRepository;
        this.jwtUtil                = jwtUtil;
        this.tagRepository          = tagRepository;
        this.tagMapRepository       = tagMapRepository;
        this.articleVoteRepository  = articleVoteRepository;
        this.replyRepository        = replyRepository;
    }

    // TODO 全部文章查询接口
    @GetMapping
    public ResultVo getAll(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        // 时间倒叙查找
        resultVo.setResult(articleRepository.findAll(new PageRequest(0,15,new Sort(Sort.Direction.DESC,"date"))).getContent());
        return resultVo;
    }

    // 获取文章下全部回复
    @GetMapping("{articleId}/reply")
    public GenericVo getAllReply(@PathVariable Long articleId,@RequestParam(defaultValue = "0") int page){
        // 判断问题是否存在
        if (! articleRepository.exists(articleId)){
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(400);
            messageVo.setMessage("文章不存在");
            return messageVo;
        }
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        // 时间逆序查找回复
        resultVo.setResult(replyRepository.findAllByArticleIdAndPass(articleId,true,new PageRequest(page,15,new Sort(Sort.Direction.DESC,"date"))));
        return resultVo;
    }

    // 根据传过来的文章id获取对应的文章
    @GetMapping("/{articleId}")
    public GenericVo getId(@PathVariable Long articleId){
        // 判断问题是否存在
        if (! articleRepository.exists(articleId)){
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(400);
            messageVo.setMessage("文章不存在");
            return messageVo;
        }

        // 根据问题id查找问题
        Article article = articleRepository.findOne(articleId);
        ArticleVo articleVo = new ArticleVo();

        // 获取各个可以暴露出去的字段
        articleVo.setId(article.getId());
        articleVo.setTitle(article.getTitle());
        articleVo.setTitle(article.getContent());
        articleVo.setDate(article.getDate());

        // 查找文章的标签映射
        List<TagMap> tagMapEntities = tagMapRepository.findAllByCorrelationAndType(articleId,"article");
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
    @DeleteMapping("/delete/{id}")
    public MessageVo delete(@PathVariable Long articleId, @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtil.getUid(token);
        MessageVo messageVo = new MessageVo();
        // 判断问题是否存在
        if (! articleRepository.exists(articleId)){
            messageVo.setCode(400);
            messageVo.setMessage("文章不存在");
        }
        // 判断问题是否属于用户
        else if (! articleRepository.belong(articleId,uid)){
            messageVo.setCode(400);
            messageVo.setMessage("无法删除不属于你的文章");
        }
        else{
            articleRepository.delete(articleId);
            messageVo.setCode(200);
            messageVo.setMessage("删除成功");
        }
        return messageVo;
    }

    // 发布一个新的文章
    @PostMapping("/post")
    public MessageVo post(@RequestParam String title,
                          @RequestParam String content,
                          @RequestParam String[] tags,
                          @RequestParam String replyStatus,
                          @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtil.getUid(token);

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
            Article article = new Article();
            article.setUid(uid);
            article.setTitle(title);
            article.setContent(content);
            article.setReplyStatus(ReplyStatusType.valueOf(replyStatus));
            articleRepository.save(article);
            // 标签都合法保存下来
            for (String tagName : tags) {
                TagMap tagMap = new TagMap();
                tagMap.setCorrelation(article.getId());
                tagMap.setTagId(tagRepository.findByNameIgnoreCase(tagName).getId());
                tagMap.setType("article");
                tagMapRepository.save(tagMap);
            }
            // 设置返回信息
            messageVo.setCode(200);
            messageVo.setMessage("文章发布成功");
        }
        return messageVo;
    }

    // 文章点赞
    @PostMapping("/vote")
    public GenericVo vote(@RequestParam Long articleId,
                          @RequestHeader("token") String token) {
        // 获取uid
        String uid = jwtUtil.getUid(token);
        MessageVo messageVo = new MessageVo();
        if (!articleRepository.exists(articleId)){
            messageVo.setCode(404);
            messageVo.setMessage("文章不存在");
        }
        // 如果已点赞
        else if (articleVoteRepository.isVoted(articleId,uid)){
            articleVoteRepository.deleteByArticleIdAndUid(articleId,uid);
            messageVo.setCode(200);
            messageVo.setMessage("已取消点赞");
        }
        // 如果没有点赞
        else{
            ArticleVote articleVote = new ArticleVote();
            articleVote.setArticleId(articleId);
            articleVote.setUid(uid);
            articleVoteRepository.save(articleVote);
            messageVo.setCode(200);
            messageVo.setMessage("点赞成功");
        }
        return messageVo;
    }
}

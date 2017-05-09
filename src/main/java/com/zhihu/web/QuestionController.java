package com.zhihu.web;

import com.zhihu.domain.comment.CommentEntity;
import com.zhihu.domain.comment.CommentRepository;
import com.zhihu.domain.quesstion.QuestionEntity;
import com.zhihu.domain.quesstion.QuestionRepository;
import com.zhihu.domain.tag.TagRepository;
import com.zhihu.domain.tag.tagMap.TagMapEntity;
import com.zhihu.domain.tag.tagMap.TagMapRepository;
import com.zhihu.util.BaseUtil;
import com.zhihu.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by fz on 17-3-31.
 */
@RestController
@RequestMapping(value = "/question")
public class QuestionController {
    // 问题
    @Autowired private QuestionRepository questionRepository;
    // 标签
    @Autowired private TagRepository tagRepository;
    // 标签映射
    @Autowired private TagMapRepository tagMapRepository;
    // 评论
    @Autowired private CommentRepository commentRepository;

    /**
     * 根据传过来的id获取某个问题
     * @param id 文章的id
     * @return id对应的问题
     */
    @GetMapping(value = "/{id}", produces="application/json;charset=UTF-8")
    public QuestionEntity getId(@PathVariable("id") Long id) {
        return questionRepository.findById(id);
    }


    /**
     * 获取用户发表的所有的问题
     * @param session
     * @return 获取用户发表的所有的问题
     */
    @GetMapping(value = "/select", produces="application/json;charset=UTF-8")
    public List<QuestionEntity> all(HttpSession session){
        String userId = ((UserInfoVO)session.getAttribute("data")).getId();
        // TODO 查找问题所属的标签
        //        List<TagMapEntity> tagMapEntities = tagMapRepository.findAllByTagIdAndType(id,"question");
//        List list = new LinkedList();
//        tagMapEntities.stream().forEach(map -> {
//            System.out.println(map);
//            list.add(tagRepository.findById(map.getTagId()).getName());
//        });

        return questionRepository.findAllByUid(userId);
    }

    /**
     * 根据传过来的id删除某个问题
     * @param id 问题的id
     * @param session
     * @return 返回状态,问题删除成功或者失败
     */
    @DeleteMapping(value = "/delete/{id}", produces="application/json;charset=UTF-8")
    public String delete(@PathVariable(value = "id") Long id,HttpSession session){
        String userId = ((UserInfoVO)session.getAttribute("data")).getId();
        String result = null;
        if (questionRepository.deleteById(id) == 1)
            result = BaseUtil.formatResult(200,"删除问题成功");
        else
            result = BaseUtil.formatResult(400,"删除问题失败");
        return result;
    }

    /**
     * 根据id以及传过来的标题,内容修改
     * 对应的问题
     * @param id 问题的id
     * @param title 问题的标题
     * @param content 问题的内容
     * @param session
     * @return 返回状态,修改成功或者失败
     */
    @PostMapping(value = "/modify", produces="application/json;charset=UTF-8")
    public String modify(@RequestParam(value = "id") Long id,
                         @RequestParam(value = "title") String title ,
                         @RequestParam(value = "content",defaultValue = "") String content,
                         HttpSession session){
        String uid = ((UserInfoVO)session.getAttribute("data")).getId();
        String result = null;
        if (questionRepository.updateContentByIdAndUid(id,uid,title,content) == 1)
            result = BaseUtil.formatResult(200,"问题修改成功");
        else
            result = BaseUtil.formatResult(400,"问题修改失败");
        return result;
    }

    /**
     * 发布一个新的问题,根据session
     * 里面查到的用户,先判断问题是否
     * 已经存在在数据库里面了,如果存在
     * 返回失败,不存在则发布
     * @param title 问题的标题
     * @param content 问题的内容
     * @param session
     * @return 先判断问题是否已经存在在
     * 数据库里面了如果存在返回失败,不存
     * 在则发布
     */
    @PostMapping(value = "/post", produces="application/json;charset=UTF-8")
    public String post(@RequestParam(value = "title") String title,
                       @RequestParam(value = "content") String content,
                       @RequestParam(value = "tag") Long[] tags,
                       HttpSession session) {
        QuestionEntity questionEntity = new QuestionEntity();
        String  uid = ((UserInfoVO)session.getAttribute("data")).getId();
        for (long s : tags)
            if(!tagRepository.exists(s))
                return BaseUtil.formatResult(400,"无效的标签");
        // --------------------------------
        questionEntity.setUid(uid);
        questionEntity.setTitle(title);
        questionEntity.setContent(content);
        questionRepository.save(questionEntity);
        for (long s : tags){
            TagMapEntity tagMapEntity = new TagMapEntity();
            tagMapEntity.setCorrelation(questionEntity.getId());
            tagMapEntity.setTagId(s);
            tagMapEntity.setType("question");
            tagMapRepository.save(tagMapEntity);
        }
        // tagNumber
        String result = BaseUtil.formatResult(200,"问题发布成功");
        return result;
    }

    // 查找问题的评论
    @GetMapping(value = "/comment/{id}", produces="application/json;charset=UTF-8")
    public String selectById(@PathVariable("id") Long id){
        List<CommentEntity> list = commentRepository.findAllByCorrelationAndType(id,"question");
        return BaseUtil.formatResult(200,"",list);
    }
}

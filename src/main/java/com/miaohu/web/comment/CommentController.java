package com.miaohu.web.comment;

import com.miaohu.domain.comment.CommentEntity;
import com.miaohu.domain.comment.CommentRepository;
import com.miaohu.util.JsonUtil;
import com.miaohu.service.getUserInfo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Nbsaw on 17-5-3.
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired private CommentRepository commentRepository;

    @PostMapping(value = "/add", produces="application/json;charset=UTF-8")
    public String comment(@RequestParam(value = "correlation") Long correlation,
                          @RequestParam(value = "content") String content,
                          @RequestParam(value = "type") String type,
                          HttpSession session){
        String result = null;
        // 回复内容不能为空
        if (content.trim().length() == 0){
            result = JsonUtil.formatResult(200,"评论成功");
        }
        // 判断是否为文章或者问题
        else if (!type.equals("question") || !type.equals("article")){
            result = JsonUtil.formatResult(400,"错误的类型");
        }
        // 
        else{
            String userId = ((UserInfoVO)session.getAttribute("data")).getId();
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setUid(userId);
            commentEntity.setCorrelation(correlation);
            commentEntity.setContent(content);
            commentEntity.setType(type);
            commentRepository.save(commentEntity);
            result = JsonUtil.formatResult(200,"评论成功");
        }
        return result;
    }
}

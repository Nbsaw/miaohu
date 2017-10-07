package com.nbsaw.miaohu.validator;

import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.common.StringUtils;
import com.nbsaw.miaohu.service.QuestionService;
import com.nbsaw.miaohu.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class QuestionValidator {

    private final QuestionService questionService;
    private final TagService tagService;

    // ----------暴露方法----------

    public ErrorsMap titleValid(String title){
        ErrorsMap errors = new ErrorsMap();
        titleValid(title,errors);
        return errors;
    }

    public ErrorsMap postValid(String title, String content, String[] tags){
        ErrorsMap errors = new ErrorsMap();
        titleValid(title,errors);
        // TODO 内容文字过滤
        tagsValid(tags,errors);
        return errors;
    }

    public ErrorsMap questionIdValid(Long questionId){
        ErrorsMap errors = new ErrorsMap();
        if (!existsById(questionId))
            errors.put("questionId","问题id不存在");
        return errors;
    }

    public ErrorsMap belongValid(Long questionId,String uid){
        ErrorsMap errors  = new ErrorsMap();
        if (!isBelong(questionId,uid))
            errors.put("belong","无法删除不属于你的问题");
        return errors;
    }

    // ----------入口方法----------

    // 标题判断
    private void titleValid(String title,ErrorsMap errorsMap){
        if (isTitleEmpty(title))
            errorsMap.put("title","标题不能为空哦");
        else if (isTitleTooShort(title))
            errorsMap.put("title","标题字数不能少于3个哦");
        else if (isTitleTooLong(title))
            errorsMap.put("title","标题字数不能超过51个哦");
        else if (hasQuestionMark(title))
            errorsMap.put("title","你忘记给标题加问号了");
        else if (existsByTitle(title))
            errorsMap.put("title","已经有一样的问题了");
    }

    // 标签判断
    private void tagsValid(String[] tags,ErrorsMap errorsMap){
        for (String tagName : tags){
            if (!tagService.exists(tagName)){
               errorsMap.put("tags","标签不存在");
                break;
            }
        }
    }

    // ----------辅助方法----------

    // 判断标题是否为空
    private boolean isTitleEmpty(String title){
        return StringUtils.isEmpty(title);
    }

    // 判断标题字数过少
    private boolean isTitleTooShort(String title){
        return title.length() < 3;
    }

    // 判断标题字数过长
    private boolean isTitleTooLong(String title){
        return title.length() > 51;
    }

    // 判断标题是否包含空格
    private boolean hasQuestionMark(String title){
        String last = title.substring(title.length() - 1); // 最后一个字符
        return !last.equals("?") && !last.equals("？");
    }

    // 判断标题是否存在数据库
    private boolean existsByTitle(String title){
        return questionService.existsByTitle(title);
    }

    // 判断对应id的问题是否存在
    private boolean existsById(Long questionId){ return questionService.existsById(questionId); }

    // 判断问题是否属于用户
    private boolean isBelong(Long questionId,String uid){
        return questionService.belong(questionId,uid);
    }

}

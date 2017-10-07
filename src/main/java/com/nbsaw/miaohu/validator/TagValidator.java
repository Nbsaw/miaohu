package com.nbsaw.miaohu.validator;

import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.common.StringUtils;
import com.nbsaw.miaohu.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class TagValidator {

    private final TagService tagService;

    // ----------暴露方法----------

    public ErrorsMap validTag(String tagName){
        ErrorsMap errors = new ErrorsMap();
        if (isExists(tagName))
            errors.put("tags","已经存在的标签");
        else if (StringUtils.isEmpty(tagName))
            errors.put("tags","标签不能为空");
        else if (hasSpace(tagName))
            errors.put("tags","标签不能包含空格!");
        return errors;
    }

    // ----------辅助方法----------

    private boolean isExists(String tagName){
        return tagService.exists(tagName);
    }

    private boolean hasSpace(String tagName){
        return tagName.contains(" ");
    }

}

package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.common.JsonResult;
import com.nbsaw.miaohu.exception.ValidParamException;
import com.nbsaw.miaohu.service.AnswerService;
import com.nbsaw.miaohu.common.JwtUtils;
import com.nbsaw.miaohu.validator.AnswerValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class AnswerController {

    private final JwtUtils jwtUtils;
    private final AnswerService service;
    private final AnswerValidator validator;

    // 查找某个问题下的前5个回答
    @GetMapping("/{questionId}")
    public JsonResult selectAnswerById(@PathVariable Long questionId,
                                       @RequestParam(value = "page",defaultValue = "0") int page) {
        return new JsonResult(0,"",service.findAllByQuestionId(questionId,page));
    }

    // 回答问题
    @PostMapping("/add")
    public JsonResult add(@RequestParam Long questionId,
                          @RequestParam String content,
                          @RequestHeader("${jwt.header}") String token) throws ValidParamException {
        Long uid = jwtUtils.getUid(token);
        ErrorsMap errors = validator.addValid(questionId,uid);
        if (errors.hasError())
            return new JsonResult(400,"回答失败",errors);
        service.save(questionId,uid,content);
        return new JsonResult(0,"回答成功",errors);
    }

//     回答删除
    @DeleteMapping("/delete")
    public JsonResult deleteAnswer(@RequestParam Long answerId,
                                   @RequestHeader String token) {
        // 获取uid
        Long uid = jwtUtils.getUid(token);
        ErrorsMap errors = validator.deleteValid(answerId,uid);
        if (errors.hasError())
            return new JsonResult(400,"回答删除失败",errors);
        service.delete(answerId);
        return new JsonResult(0,"回答删除成功");
    }

    // TODO 推送点赞
    @PutMapping("/{answerId}//vote")
    public JsonResult vote(@PathVariable Long answerId,
                           @RequestHeader("${jwt.header}") String token){
        Long uid = jwtUtils.getUid(token);
        ErrorsMap errorsMap = validator.voteValid(answerId);
        if (errorsMap.hasError())
            return new JsonResult(400,"回答点赞失败",errorsMap);
        if (service.vote(answerId,uid))
            return new JsonResult(0,"回答点赞成功");
        return new JsonResult(0,"回答取消点赞成功");
    }
}

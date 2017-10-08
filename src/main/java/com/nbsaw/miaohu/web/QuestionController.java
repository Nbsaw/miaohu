package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.common.JsonResult;
import com.nbsaw.miaohu.common.JwtUtils;
import com.nbsaw.miaohu.service.QuestionService;
import com.nbsaw.miaohu.validator.QuestionValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class QuestionController {

    private final JwtUtils jwtUtils;
    private final QuestionValidator validator;
    private final QuestionService service;

    // 验证问题标题是否合法
    @PostMapping("/valid")
    public JsonResult validTitle(@RequestParam String title) {
        ErrorsMap errors = validator.titleValid(title);
        if (errors.hasError()){
            return new JsonResult(400,"并不可以发布这个问题",errors);
        }else{
            return new JsonResult(0,"这是一个可以发布的问题");
        }
    }

    // 发布一个新的问题
    @PostMapping("/post")
    public JsonResult post(@RequestParam String title,
                           @RequestParam String content,
                           @RequestParam String[] tags,
                           @RequestParam boolean anonymous,
                           @RequestHeader("${jwt.header}") String token) {
        Long uid = jwtUtils.getUid(token);
        ErrorsMap errors = validator.postValid(title,content,tags);
        if (errors.hasError()){
            return new JsonResult(400,"问题发布失败",errors);
        }else{
            service.save(uid,title,content,tags,anonymous);
            return new JsonResult(0,"问题发布成功");
        }
    }

    // 查询近期发布的问题
    @GetMapping
    public JsonResult all(@RequestParam(defaultValue = "0") int page){
        return new JsonResult(0,"",service.findAllQuestion(page));
    }

    // 根据传过来的问题id获取对应的问题
    @GetMapping("/{questionId}")
    public JsonResult getId(@PathVariable Long questionId, @RequestHeader("${jwt.header}") String token) {
        ErrorsMap errors = validator.questionIdValid(questionId);
        if (errors.hasError())
            return new JsonResult(HttpStatus.BAD_REQUEST,"问题查询失败",errors);
        else
            return new JsonResult(0,"",service.findOne(questionId,jwtUtils.getUid(token)));
    }

//    // 根据传过来的问题id删除对应的问题
//    @DeleteMapping("/delete/{questionId}")
//    public JsonResult delete(@PathVariable Long questionId, @RequestHeader("${jwt.header}") String token) {
//        ErrorsMap idValid = validator.questionIdValid(questionId);
//        if (idValid.hasError())
//            return new JsonResult(HttpStatus.BAD_REQUEST,"问题删除失败",idValid);
//
//        ErrorsMap belongValid = validator.belongValid(questionId,jwtUtils.getUid(token));
//        if (belongValid.hasError())
//            return new JsonResult(HttpStatus.FORBIDDEN,"问题删除失败",belongValid);
//
//        service.delete(questionId);
//        return new JsonResult(0,"问题删除成功");
//    }

//    // 设置(问题,回答)都为匿名 / 取消匿名
//    @PostMapping("/anonymous")
//    public JsonResult setAnonymous(@RequestParam Long questionId, @RequestHeader("${jwt.header}") String token) {
//
//        Long uid = jwtUtils.getUid(token);
//
//        ErrorsMap idValid = validator.questionIdValid(questionId);
//        if (idValid.hasError())
//            return new JsonResult(HttpStatus.BAD_REQUEST,"设置失败",idValid);
//
//        ErrorsMap belongValid = validator.belongValid(questionId,uid);
//        if (belongValid.hasError())
//            return new JsonResult(HttpStatus.FORBIDDEN,"设置失败",belongValid);
//
//        boolean isAnonymous = service.setAnonymous(questionId,uid);
//
//        if (isAnonymous)
//            return new JsonResult(0,"已取消匿名");
//        else
//            return new JsonResult(0,"已经设置为匿名!");
//    }

//     // TODO 加上标签字段
//     // TODO 问题修改历史
//    // TODO 加个问题修改理由表
//    // 根据id以及传过来的标题,内容修改对应的问题
//    @PostMapping("/modify")
//    public JsonResult modify(@RequestParam Long questionId,
//                             @RequestParam String title,
//                             @RequestParam(defaultValue = "") String content,
//                             @RequestParam boolean anonymous,
//                             @RequestHeader("${jwt.header}") String token) {
//
//        Long uid = jwtUtils.getUid(token);
//
//        ErrorsMap idValid = validator.questionIdValid(questionId);
//        if (idValid.hasError())
//            return new JsonResult(HttpStatus.BAD_REQUEST,"问题修改失败",idValid);
//
//        ErrorsMap belongValid = validator.belongValid(questionId,uid);
//        if (belongValid.hasError())
//            return new JsonResult(HttpStatus.FORBIDDEN,"问题修改失败",belongValid);
//
//        service.update(questionId,uid,title,content,anonymous);
//         return new JsonResult(0,"问题修改成功");
//    }
}


package com.nbsaw.miaohu.handler;

import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.vo.MessageVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler{

    @Value("${spring.http.multipart.max-file-size}")
    private String maxSize;

    // 参数缺少无效错误处理
    @ExceptionHandler(value = {MissingServletRequestParameterException.class,IllegalArgumentException.class})
    @ResponseBody
    public MessageVo jsonErrorHandler(Exception e) throws Exception {
        MessageVo error = new MessageVo();
        error.setCode(400);
        error.setMessage(e.getMessage());
        return error;
    }

    // 文件大小限制
    @ExceptionHandler(value = MultipartException.class)
    @ResponseBody
    public MessageVo uploadErrorHandler(MultipartException e) throws Exception {
        MessageVo error = new MessageVo();
        if (e.getCause().getMessage().contains("org.apache.tomcat.util.http.fileupload.FileUploadBase$FileSizeLimitExceededException")){
            error.setCode(400);
            error.setMessage("文件大小不应该超过" + maxSize);
        }else{
            error.setCode(400);
            error.setMessage("文件上传错误");
        }
        return error;
    }

    // 登陆异常处理
    @ExceptionHandler(value = {InValidJwtException.class,ExJwtException.class})
    @ResponseBody
    public MessageVo loginErrorHandler(Exception e) throws Exception {
        MessageVo error = new MessageVo();
        error.setCode(400);
        error.setMessage(e.getMessage());
        return error;
    }

    // 404 错误处理
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public MessageVo requestHandlingNoHandlerFound() {
        MessageVo error = new MessageVo();
        error.setCode(404);
        error.setMessage("router is not exists");
        return error;
    }

}

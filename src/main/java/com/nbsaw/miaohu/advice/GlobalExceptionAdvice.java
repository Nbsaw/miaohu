package com.nbsaw.miaohu.advice;

import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.*;

@RestControllerAdvice
class GlobalExceptionAdvice {

    @Value("${spring.http.multipart.max-file-size}")
    private String maxSize;

    // 参数缺少错误处理
//    // TODO 这里要改
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String,String> paramMissErrorHandler(MissingServletRequestParameterException e) throws Exception {
//        Map error = new HashMap();
//
//        error.put("error",e.getMessage());
//        return error;
//    }

    // 参数错误处理
    // TODO 这里要改
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> illegalArgumentErrorHandler(IllegalArgumentException e) throws Exception {
        Map message = new HashMap();
        message.put("error",e.getMessage());
        return message;
    }

    // 上传文件大小限制
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> uploadErrorHandler(MultipartException e) throws Exception {
        Map message = new HashMap();
        if (e.getCause().getMessage().contains("org.apache.tomcat.utils.http.fileupload.FileUploadBase$FileSizeLimitExceededException")){
            message.put("error","文件大小不应该超过" + maxSize);
        }else{
            message.put("error","文件上传错误");
        }
        return message;
    }

    // 登陆异常处理
    @ExceptionHandler({InValidJwtException.class,ExJwtException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,String> loginErrorHandler(Exception e) throws Exception {
        Map message = new HashMap();
        message.put("error",e.getMessage());
        return message;
    }

    // 404 错误处理 -> 主要针对路由不存在的处理
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> requestHandlingNoHandlerFound() {
        Map message = new HashMap();
        message.put("error","router is not exists");
        return message;
    }

}
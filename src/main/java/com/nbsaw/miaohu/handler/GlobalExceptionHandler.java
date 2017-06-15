package com.nbsaw.miaohu.handler;
import com.nbsaw.miaohu.vo.MessageVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by nbsaw on 2017/6/13.
 * 错误处理
 */
@ControllerAdvice
public class GlobalExceptionHandler{

    // 参数缺少无效错误处理
    @ExceptionHandler(value = {MissingServletRequestParameterException.class,IllegalArgumentException.class})
    @ResponseBody
    public MessageVo jsonErrorHandler(Exception e) throws Exception {
        MessageVo error = new MessageVo();
        error.setCode(400);
        error.setMessage(e.getMessage());
        return error;
    }

    // 404 错误处理
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public MessageVo requestHandlingNoHandlerFound() {
        MessageVo error = new MessageVo();
        error.setCode(404);
        error.setMessage("router is not exists");
        return error;
    }

}

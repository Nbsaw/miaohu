package com.nbsaw.miaohu.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class JsonResult {
    // 状态码：1成功，其他为失败
    public int code;

    // 成功为success，其他为失败原因
    public String message;

    // 数据结果集
    public Object data;

    public JsonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JsonResult(HttpStatus code, String message, Object data) {
        this.code = code.value();
        this.message = message;
        this.data = data;
    }
}

package com.nbsaw.miaohu.vo;
import lombok.Data;

/**
 * Created by nbsaw on 2017/6/13.
 */
@Data
public class ErrorInfoVo {
    // 状态码
    private Integer code;

    // 错误信息
    private String message;
}
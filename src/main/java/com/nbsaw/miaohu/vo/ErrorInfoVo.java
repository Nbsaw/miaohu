package com.nbsaw.miaohu.vo;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by nbsaw on 2017/6/13.
 */
@Data
public class ErrorInfoVo implements Serializable {
    // 状态码
    private Integer code;

    // 错误信息
    private String message;
}
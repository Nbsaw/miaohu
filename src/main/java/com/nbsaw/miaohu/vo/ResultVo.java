package com.nbsaw.miaohu.vo;

import lombok.Data;

/**
 * Created by nbsaw on 2017/6/15.
 * 返回结果的vo
 */
@Data
public class ResultVo<T> extends GenericVo {
    // 结果
    private T result;
}

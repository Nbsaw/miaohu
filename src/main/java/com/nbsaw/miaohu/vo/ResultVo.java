package com.nbsaw.miaohu.vo;

import lombok.Data;

@Data
public class ResultVo<T> extends GenericVo {
    private T result;
}

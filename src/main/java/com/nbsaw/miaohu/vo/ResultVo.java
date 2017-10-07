package com.nbsaw.miaohu.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResultVo<T> extends GenericVo {

    private T result;

    public ResultVo(){}

    public ResultVo(int code,T result){
        setCode(code);
        setResult(result);
    }

}
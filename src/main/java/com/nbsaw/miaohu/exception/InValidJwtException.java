package com.nbsaw.miaohu.exception;

/**
 * Created by nbsaw on 6/25/2017.
 */
public class InValidJwtException extends Exception{
    public InValidJwtException(){}
    public InValidJwtException(String msg){
        super(msg);
    }
    @Override
    public String getMessage() {
        return "无效的token";
    }
}

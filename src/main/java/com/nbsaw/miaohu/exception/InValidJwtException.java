package com.nbsaw.miaohu.exception;

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

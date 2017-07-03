package com.nbsaw.miaohu.exception;

public class ExJwtException extends Exception{

    public ExJwtException(){}

    public ExJwtException(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return "token已超时";
    }

}

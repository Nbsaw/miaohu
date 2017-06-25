package com.nbsaw.miaohu.exception;

/**
 * Created by nbsaw on 6/25/2017.
 */
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

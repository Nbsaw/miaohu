package com.nbsaw.miaohu.common;

import java.util.HashMap;
import java.util.Map;

public class ErrorsMap {
    private Map<String,String> errors = new HashMap<>();

    public void put(String key , String value){
        if (StringUtils.notEmpty(value))
            errors.put(key,value);
    }

    public boolean hasError(){
        return errors.size() != 0;
    }

    public Map<String,String> getErrors(){
        return errors;
    }
}

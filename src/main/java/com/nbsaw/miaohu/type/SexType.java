package com.nbsaw.miaohu.type;

import java.util.Objects;

public enum SexType {
    male("男"),
    female("女");

    private String value;

    SexType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static SexType fromString(String value){
        Objects.requireNonNull(value, "value can not be null");
        SexType sexType = null;
        if("男".equals(value)){
            sexType = male;
        }
        else if("女".equals(value)){
            sexType = female;
        }
        return sexType;
    }
}

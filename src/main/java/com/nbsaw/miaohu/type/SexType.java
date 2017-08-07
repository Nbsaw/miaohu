package com.nbsaw.miaohu.type;


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
        SexType sexType = null;
        if("male".equals(value)){
            sexType = male;
        }
        else if("female".equals(value)){
            sexType = female;
        }
        return sexType;
    }
}

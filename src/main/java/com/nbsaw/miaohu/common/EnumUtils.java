package com.nbsaw.miaohu.common;

public class EnumUtils {
    public static  <T extends Enum<T>> boolean equalsOf(Class<T> clazz, String value){
        for (T t : clazz.getEnumConstants()) {
            if (t.toString().equals(value)) return true;
        }
        return false;
    }
}

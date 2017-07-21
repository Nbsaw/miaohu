package com.nbsaw.miaohu.util;

public class EnumUtil {
    public static  <T extends Enum<T>> boolean equalsOf(Class<T> clazz, String value){
        for (T t : clazz.getEnumConstants()) {
            if (t.toString().equals(value)) return true;
        }
        return false;
    }
}

package com.nbsaw.miaohu.common;

public class StringUtils {
    public static boolean isEmpty(String arg){
        return arg == null || arg.replace(" ","").length() == 0;
    }

    public static boolean notEmpty(String arg){
        return !isEmpty(arg);
    }

    public static String lastString(String arg){
        return arg.substring(arg.length() - 1);
    }
}

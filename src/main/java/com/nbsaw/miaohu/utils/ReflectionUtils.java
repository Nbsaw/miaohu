package com.nbsaw.miaohu.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class ReflectionUtils {
    // 扫描包
    public static List<String> scanPackage(String path){
        List<String> clazzs = new LinkedList<>();
        try {
            path = path.replaceAll("\\.","/");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources(path);
            File[] files = new File(resources.nextElement().getFile()).listFiles();
            for (File file: files) {
                if (file.isDirectory()) {
                    scanPackage(path + "/" + file.getName());
                }
                else{
                    String currentName = path.replace("/", ".") + "." + file.getName().substring(0, file.getName().length() - 6);
                    clazzs.add(currentName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clazzs;
    }
}

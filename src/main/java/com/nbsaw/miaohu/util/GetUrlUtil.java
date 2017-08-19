package com.nbsaw.miaohu.util;

import com.nbsaw.miaohu.controller.QuestionController;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

public class GetUrlUtil {
    private static List<String> result = null;

    private static void addInfix(List list,String[] urls){
        for (String url : urls){
            list.add(url);
        }
    }

    // 扫描类
    private static void scanClass(Class klazz){
        // 检查是否有@RequestHeader("token")参数
        // 主要是怕日后有其他用了header参数的会留坑
        Method[] methods = klazz.getMethods();
        List<String> infix = new LinkedList();
        for (Method method : methods){
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters){
                if (parameter.getAnnotation(RequestHeader.class) != null
                        && parameter.getAnnotation(RequestHeader.class).value().equals("token")){
                    // 判断是哪一种注解
                    for (Annotation annotation : method.getAnnotations()){
                        if (annotation instanceof GetMapping){
                            addInfix(infix,((GetMapping) annotation).value());
                        }
                        else if (annotation instanceof PostMapping){
                            addInfix(infix,((PostMapping) annotation).value());
                        }
                        else if (annotation instanceof PutMapping){
                            addInfix(infix,((PutMapping) annotation).value());
                        }
                        else if (annotation instanceof DeleteMapping){
                            addInfix(infix,((DeleteMapping) annotation).value());
                        }
                    }
                }
            }
        }
        // 如果中缀不为空尝试获取前缀
        if (infix.size() > 0){
            // 前缀获取
            String prefix[] = null;
            if (klazz.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMapping = (RequestMapping) klazz.getAnnotation(RequestMapping.class);
                prefix = requestMapping.value();
            }
            // 前缀不为空添加前缀
            if (prefix.length > 0){
                for (String p : prefix){
                    for (String i : infix)
                        result.add(p + i);
                }
            }
            // 前缀为空直接把中缀加到url列表里
            else
                result.addAll(infix);

        }
    }

    public static String[] getUrl(String path){
        if (result != null) return result.toArray(new String[result.size()]);
        result = new LinkedList<>();
        // 获取包下的类
        List<String> clazzs = ReflectionUtil.scanPackage(path);
        // 扫描类
        for (String className : clazzs){
            try {
                scanClass(Class.forName(className));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result.toArray(new String[result.size()]);
    }
}

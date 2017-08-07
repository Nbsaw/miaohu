package com.nbsaw.miaohu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil{
    private static String filter(String html,String regex , int enums){
        Pattern p_script = Pattern.compile(regex,enums);
        Matcher m_script = p_script.matcher(html);
        return m_script.replaceAll("");
    }

    public static String getInnerText(String html){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>";
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>";
        String regEx_html="<[^>]+>";

        // filter script tag
        html = filter(html,regEx_script,Pattern.CASE_INSENSITIVE);

        // filter style tag
        html = filter(html,regEx_style,Pattern.CASE_INSENSITIVE);

        // filter html tag
        html = filter(html,regEx_html,Pattern.CASE_INSENSITIVE);

        return html.trim();
    }
}
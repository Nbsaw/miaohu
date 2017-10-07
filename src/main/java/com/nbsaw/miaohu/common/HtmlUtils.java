package com.nbsaw.miaohu.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {
    private static String filter(String html,String regex){
        Pattern p_script = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(html);
        return  m_script.replaceAll("");
    }

    public static String getInnerText(String html){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>";
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>";
        String regEx_html="<[^>]+>";

        // filter script tag
        html = filter(html,regEx_script);

        // filter style tag
        html = filter(html,regEx_style);

        // filter html tag
        html = filter(html,regEx_html);

        return html.trim();
    }
}
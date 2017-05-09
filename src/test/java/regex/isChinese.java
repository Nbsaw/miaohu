package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fz on 17-4-11.
 */
public class isChinese {
    // 判断字符是否为中文
    public static boolean isChinese(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,50}$");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }
    public static void main(String[] args){
        System.out.println(isChinese("啊ab"));
    }
}

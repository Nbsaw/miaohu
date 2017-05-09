package regex;

/**
 * Created by Nbsaw on 17-5-7.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Eite {
    // 判断字符是否为中文
    public static boolean isChinese(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("^@.+[5]$");
        Matcher m = p.matcher(str);
        System.out.println(m.toMatchResult().toString());
        return temp;
    }
    public static void main(String[] args){
        //
        //        System.out.println(isChinese("啊ab撒旦@vzch"));
    }
}

package com.nbsaw.miaohu.test;

import com.nbsaw.miaohu.type.UserType;
import com.nbsaw.miaohu.util.EnumUtil;
import com.nbsaw.miaohu.util.GetUrlUtil;
import com.nbsaw.miaohu.util.HtmlUtil;
import com.nbsaw.miaohu.util.RegisterValidUtil;
import junit.framework.TestCase;

public class UtilsTest extends TestCase {

    public void testHtmlUtil(){
        String html = "<script>for(;;)alert('xss')</script><p><div>M<img src='' alt='fuck' /></div></p>";
        html = HtmlUtil.getInnerText(html);
        assertEquals(html,"M");
    }


    public void testEnumUtil(){
        assertEquals(EnumUtil.equalsOf(UserType.class,"HACK"),false);
        assertEquals(EnumUtil.equalsOf(UserType.class,"USER"),true);
    }

    public void testRegisterValidUtil(){
        //  中文测试
        assertEquals(RegisterValidUtil.isValidName("刘看山"),true);
        assertEquals(RegisterValidUtil.isValidName("ほほほ"),false);
    }
}

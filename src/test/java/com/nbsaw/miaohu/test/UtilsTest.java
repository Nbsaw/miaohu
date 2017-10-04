package com.nbsaw.miaohu.test;

import com.nbsaw.miaohu.type.UserType;
import com.nbsaw.miaohu.utils.EnumUtils;
import com.nbsaw.miaohu.utils.HtmlUtils;
import junit.framework.TestCase;

public class UtilsTest extends TestCase {

    public void testHtmlUtil(){
        String html = "<script>for(;;)alert('xss')</script><p><div>M<img src='' alt='fuck' /></div></p>";
        html = HtmlUtils.getInnerText(html);
        assertEquals(html,"M");
    }


    public void testEnumUtil(){
        assertEquals(EnumUtils.equalsOf(UserType.class,"HACK"),false);
        assertEquals(EnumUtils.equalsOf(UserType.class,"USER"),true);
    }

    public void testRegisterValidUtil(){
        //  中文测试
//        assertEquals(RegisterValidUtil.isValidName("刘看山"),true);
//        assertEquals(RegisterValidUtil.isValidName("ほほほ"),false);
    }
}

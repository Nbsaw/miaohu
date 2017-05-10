package com.miaohu.service.phoneMessage;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by fz on 17-4-6.
 * 手机短信服务,使用了阿里大于
 */
@Component
public class PhoneMessageImpl implements PhoneMessageService {
    private final String URL = "http://gw.api.taobao.com/router/rest"; // api地址
    private final String APPKEY = "23737153";
    private String SECRET = "96b6c075231e49e77cf951efd6dca529";
    private static Logger logger = Logger.getLogger(PhoneMessageImpl.class);

    /**
     * 发送注册验证码到手机,返回验证码
     * @param phone 发送到手机号
     * @return 返回随机生成的6位短信验证码
     */
    @Override
    public String sendRegisterCode(String phone) {
        String random = String.valueOf((int)((Math.random()*9+1)*100000)); //随机数生成
        TaobaoClient client = new DefaultTaobaoClient(URL, APPKEY, SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal"); // 消息类型默认
        req.setSmsFreeSignName("彭佳文"); // 签名
        req.setSmsParamString("{number:'"+random+"'}"); // 六位随机验证码
        req.setRecNum(phone); // 发送到号码
        req.setSmsTemplateCode("SMS_60000851"); // 短信验证模板ID
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
            logger.info(rsp.getBody());
        } catch (ApiException e) {
            logger.error(e.getMessage());
        }
        return random;
    }
}

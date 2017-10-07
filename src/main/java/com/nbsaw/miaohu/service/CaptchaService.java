package com.nbsaw.miaohu.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface CaptchaService {

    void drawImageCaptcha(String sid, HttpServletResponse response) throws IOException;

    Map sendPhoneCaptcha(String phone);

}

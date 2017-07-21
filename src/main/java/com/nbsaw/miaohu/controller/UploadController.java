package com.nbsaw.miaohu.controller;

import com.google.gson.Gson;
import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.util.JwtUtil;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/upload")
class UploadController {

    @Autowired private JwtUtil jwtUtil;

    @PostMapping(value = "/avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) throws IOException, ExJwtException, InValidJwtException {
        // 解析token
        String uid = jwtUtil.getUid(request);

        // 凭证生成
        String accessKey = "QDmYg322MuVo4vmYAWk06I160-q9xiWKFXtZI7O3";
        String secretKey = "i-iCBJNP4_e0X1HqaWEy4zIBNpHnYi77GCUy0QFa";
        String bucket = "miaohu";
        String qiniuUrl = "http://os33nc36m.bkt.clouddn.com/";

        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);

        // 返回数据格式设置
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);

        //构造一个带指定Zone对象的配置类
        // 此处为华东机房的配置
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = uploadManager.put(avatar.getBytes(), key, upToken);

        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

        return "ok";
    }

    // TODO Tag Avatar change
}

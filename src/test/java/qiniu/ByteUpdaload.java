package qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.*;

/**
 * Created by nbsaw on 6/25/2017.
 */
public class ByteUpdaload {

    /**
     * Created by nbsaw on 6/25/2017.
     */
        public static void main(String[] args) throws IOException {
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
            // 上传
            String localFilePath = "C:\\Users\\nbsaw\\Desktop\\hagasei.jpg";
            File file = new File(localFilePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = null;
            byte[] b = new byte[1024];
            int n;
            while ((n = fileInputStream.read(b)) != -1)
                bos.write(b, 0, n);
            fileInputStream.close();
            bos.close();
            buffer = bos.toByteArray();
            Response response = uploadManager.put(buffer, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(qiniuUrl + putRet.hash);
            System.out.println("upload success !");
        }
}

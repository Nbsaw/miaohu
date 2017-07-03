package qiniu;

import com.google.gson.Gson;
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
 * QiNiu byte streams upload demo
 */
public class ByteUploadTest {
    public static void main(String[] args) throws IOException {
        // Generate token
        String accessKey = "QDmYg322MuVo4vmYAWk06I160-q9xiWKFXtZI7O3";
        String secretKey = "i-iCBJNP4_e0X1HqaWEy4zIBNpHnYi77GCUy0QFa";
        String bucket = "miaohu";
        String qiniuUrl = "http://os33nc36m.bkt.clouddn.com/";

        // Key is fileName , default key is hash of file
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);

        // Response data format setting
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);

        // Upload file to HuaDong
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);

        // Select local file upload to QiNiu Server
        String localFilePath = "C:\\Users\\nbsaw\\Desktop\\hagasei.jpg";
        File file = new File(localFilePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // Handle streams to buffer
        byte[] buffer;
        byte[] b = new byte[1024];
        int n;
        while ((n = fileInputStream.read(b)) != -1)
            bos.write(b, 0, n);
        fileInputStream.close();
        bos.close();
        buffer = bos.toByteArray();

        // Upload buffer to QiNiu Server
        Response response = uploadManager.put(buffer, key, upToken);

        // Display Response Result
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        System.out.println(qiniuUrl + putRet.hash);
        System.out.println("upload success !");
    }
}

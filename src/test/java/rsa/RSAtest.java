package rsa;

/**
 * Created by nbsaw on 2017/6/4.
 */
public class RSAtest {
    public static void main(String[] args) {
        RSAsecurity rsAsecurity = new RSAsecurity();
        System.out.println("私钥加密公钥解密例：");
        rsAsecurity.priENpubDE();
        System.out.println("公钥加密私钥解密例：");
        rsAsecurity.pubENpriDE();
    }
}
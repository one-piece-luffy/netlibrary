package com.baofu.lib;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import javax.crypto.spec.SecretKeySpec;

public class AesExample {
    public static void main(String a[]){
        String text = "http://8.210.128.221:9898";
        String key  = "87654321abcdefgh"; // 16字节的密钥
        String iv  = AesUtils.getIV();

        //随机生成key
//        SecretKeySpec secretKey = AesUtils.generateKey(128);
//        key = AesUtils.keyToBase64(secretKey);
        //ECB
        String encryptTextECB = AesUtils.encrypt(text, key);
        System.out.println("=============================");
        System.out.println("key:" + key);
        System.out.println("ECB Encrypted text:" + encryptTextECB);
        System.out.println("ECB Decrypted text:" + AesUtils.decrypt(encryptTextECB, key));
        System.out.println();

        //CBC
//        String encryptTextCBC = AesUtils.encrypt(text, key, iv, AesUtils.AES_CBC);
//        System.out.println("CBC IV:" + iv);
//        System.out.println("CBC Encrypted text:" + encryptTextCBC);
//        System.out.println("CBC Decrypted text:" + AesUtils.decrypt(encryptTextCBC, key, iv, AesUtils.AES_CBC));
//        System.out.println();
//
//        //CFB
//        String encryptTextCFB = AesUtils.encrypt(text, key, iv, AesUtils.AES_CFB);
//        System.out.println("CFB IV:" + iv);
//        System.out.println("CFB Encrypted text:" + encryptTextCFB);
//        System.out.println("CFB Decrypted text:" + AesUtils.decrypt(encryptTextCFB, key, iv, AesUtils.AES_CFB));
    }
}
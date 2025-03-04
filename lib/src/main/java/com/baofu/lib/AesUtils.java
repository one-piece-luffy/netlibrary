package com.baofu.lib;


import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtils {

    public static String encrypt(String plainText, SecretKeySpec secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, SecretKeySpec secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
    /**
     * 生成密钥
     * @param keySize
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKeySpec generateKey(int keySize)  {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.init(keySize);
        SecretKey secretKey = keyGen.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static String keyToBase64(SecretKeySpec secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static SecretKeySpec base64ToKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, "AES");
    }






    //加密模式之 ECB，AES/ECB/PKCS5Padding-----算法/模式/补码方式
    public static final String AES_ECB = "AES/ECB/PKCS5Padding";

    //加密模式之 CBC，算法/模式/补码方式
    public static final String AES_CBC = "AES/CBC/PKCS5Padding";

    //加密模式之 CFB，算法/模式/补码方式
    public static final String AES_CFB = "AES/CFB/PKCS5Padding";

    //AES 中的 IV 必须是 16 字节（128位）长
    public static final Integer IV_LENGTH = 16;

    /***
     * 空值校验
     * @param str 需要判断的值
     */
    public static boolean isEmpty(Object str) {
        return null == str || "".equals(str);
    }

    /***
     * 字符串转byte字节
     * @param str 需要转换的字符串
     */
    public static byte[] getBytes(String str){
        if (isEmpty(str)) {
            return null;
        }

        try {
            return str.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 初始化向量（IV），它是一个随机生成的字节数组，用于增加加密和解密的安全性
     */
    public static String getIV(){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < IV_LENGTH ; i++){
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /***
     * 获取一个 AES 密钥规范
     */
    public static SecretKeySpec getSecretKeySpec(String key){
        SecretKeySpec secretKeySpec = new SecretKeySpec(getBytes(key), "AES");
        return secretKeySpec;
    }

    /**
     * 加密模式 --- ECB
     * @param text 需要加密的文本内容
     * @param key 加密的密钥 key
     * */
    public static String encrypt(String text, String key){
        if (isEmpty(text) || isEmpty(key)) {
            return null;
        }

        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(AES_ECB);
            // 将密钥转换为SecretKeySpec格式
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // 初始化Cipher为加密模式，传入密钥
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            // 加密字节数组，将明文转换为字节数组进行加密
            byte[] encryptedBytes = cipher.doFinal(getBytes(text));
            // 将密文转换为 Base64 编码字符串
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密模式 --- ECB
     * @param text 需要解密的文本内容
     * @param key 解密的密钥 key
     * */
    public static String decrypt(String text, String key){
        if (isEmpty(text) || isEmpty(key)) {
            return null;
        }

        // 将密文转换为16字节的字节数组
        byte[] textBytes = Base64.getDecoder().decode(text);

        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(AES_ECB);
            // 将密钥转换为SecretKeySpec格式
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // 初始化Cipher为加密模式，传入密钥
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            // 解密字节数组,将明文转换为字节数组进行解密
            byte[] decryptedBytes = cipher.doFinal(textBytes);

            // 将明文转换为字符串
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密 - 自定义加密模式
     * @param text 需要加密的文本内容
     * @param key 加密的密钥 key
     * @param iv 初始化向量
     * @param mode 加密模式
     * */
    public static String encrypt(String text, String key, String iv, String mode){
        if (isEmpty(text) || isEmpty(key) || isEmpty(iv)) {
            return null;
        }

        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(mode);
            // 将密钥转换为SecretKeySpec格式
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // 初始化Cipher为加密模式，传入密钥和初始化向量
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(getBytes(iv)));

            // 加密字节数组，将明文转换为字节数组进行加密
            byte[] encryptedBytes = cipher.doFinal(getBytes(text));

            // 将密文转换为 Base64 编码字符串
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密 - 自定义加密模式
     * @param text 需要解密的文本内容
     * @param key 解密的密钥 key
     * @param iv 初始化向量
     * @param mode 加密模式
     * */
    public static String decrypt(String text, String key, String iv, String mode){
        if (isEmpty(text) || isEmpty(key) || isEmpty(iv)) {
            return null;
        }

        // 将密文转换为16字节的字节数组
        byte[] textBytes = Base64.getDecoder().decode(text);

        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(mode);
            // 将密钥转换为SecretKeySpec格式
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // 初始化Cipher为加密模式，传入密钥和初始化向量
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(getBytes(iv)));

            // 解密字节数组，将明文转换为字节数组进行解密
            byte[] decryptedBytes = cipher.doFinal(textBytes);

            // 将明文转换为字符串
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

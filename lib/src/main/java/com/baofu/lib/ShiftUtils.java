package com.baofu.lib;


import java.util.Base64;

/**
 * 移位加密
 */
public class ShiftUtils {

    /**
     * 先把要加密的url base64加密，再调用endcode方法。
     * @param text
     * @param diff
     * @return
     */
    public static String encode(String text, int diff) {
        String base64= Base64.getEncoder().encodeToString(text.getBytes());
        char[] c=base64.toCharArray();
        char[] passWord = new char[c.length];
        for (int i = 0; i < c.length; i++) {
            try {
                passWord[i] = (char) (c[i] + diff);
//                if ((c[i] >= 88 && c[i] <= 90) || c[i] >= 118 && c[i] <= 122)
//                    passWord[i] = (char) (c[i] + 3 - 26);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return new String(passWord);
    }

    /**
     * decode传入与encode相反的位移
     * @param password
     * @param diff
     * @return
     */
    public static String decodePassword(String password,int diff){

        String decode = encode(password, diff);
        String dencodeUrl = "";
        try {
            dencodeUrl = new String(Base64.getDecoder().decode(decode));
            return dencodeUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.baofu.lib;

public class ShiftExample {
    public static void main(String a[]){
        String text = "http://hongdou2.fun:9898";
        int diff  = 3;

        //ECB
        String encryptTextECB = ShiftUtils.encode(text, diff);
//        encryptTextECB="dKU3fGryO6k7]J<6ep{y\\ZUofl83e6D9RWj8RD@@";
        System.out.println("=============================");
        System.out.println("ECB Encrypted text:" + encryptTextECB);
        System.out.println("ECB Decrypted text:" + ShiftUtils.decode(encryptTextECB, -1*diff));
        System.out.println();

    }
}
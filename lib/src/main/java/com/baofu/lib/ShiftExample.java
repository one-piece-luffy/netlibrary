package com.baofu.lib;

public class ShiftExample {
    public static void main(String a[]){
        String text = "http://8.210.128.221:9898";
        int diff  = 3;

        //ECB
        String encryptTextECB = ShiftUtils.encode(text, 3);
        System.out.println("=============================");
        System.out.println("ECB Encrypted text:" + encryptTextECB);
        System.out.println("ECB Decrypted text:" + ShiftUtils.decode(encryptTextECB, -1*diff));
        System.out.println();

    }
}
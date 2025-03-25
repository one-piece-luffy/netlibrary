package com.baofu.lib;

public class ShiftExample {
    public static void main(String a[]){
        String text = "http://bfttget.top:9898";
        int diff  = 3;

        //ECB
        String encryptTextECB = ShiftUtils.encode(text, diff);
        System.out.println("=============================");
        System.out.println("ECB Encrypted text:" + encryptTextECB);
        System.out.println("ECB Decrypted text:" + ShiftUtils.decode(encryptTextECB, -1*diff));
        System.out.println();

    }
}
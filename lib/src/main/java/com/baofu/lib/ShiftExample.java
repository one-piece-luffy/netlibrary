package com.baofu.lib;

public class ShiftExample {
    public static void main(String a[]){
        String text = "http://8.217.111.135:9898";
        int diff  = 3;

        //ECB
        String encryptTextECB = ShiftUtils.encode(text, diff);
//        encryptTextECB="dKU3fGryO}jxPmHzOmH|RF7|PmH9RWj8RD@@";
        System.out.println("=============================");
        System.out.println("ECB Encrypted text:" + encryptTextECB);
        System.out.println("ECB Decrypted text:" + ShiftUtils.decode(encryptTextECB, -1*diff));
        System.out.println();

    }
}
package com.baofu.netlibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.http.SslError;
import android.os.Environment;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;


import java.io.File;
import java.util.List;
import java.util.Set;

public class NetUtils {
    public static boolean isMainProcess(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        if (processInfos == null)
            return true;
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info != null && info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }


    public static char[] encode(char[] c, int diff) {
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
        return passWord;
    }
    public static String decodePassword(String password,int diff){

        char[] decode = encode(password.toCharArray(), diff);
        String dencodeUrl = "";
        try {
            String a = String.valueOf(decode);
            dencodeUrl = new String(Base64.decode(a,Base64.DEFAULT), "UTF-8");
            return dencodeUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

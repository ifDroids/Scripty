package com.scripty.base;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RootUtils {
    public static boolean isDeviceRooted() {
        return checkRootMethod4();
    }
//
//    private static boolean checkRootMethod1() {
//        String buildTags = android.os.Build.TAGS;
//        return buildTags != null && buildTags.contains("test-keys");
//    }
//
//    private static boolean checkRootMethod2() {
//        String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
//                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
//        for (String path : paths) {
//            if (new File(path).exists()) return true;
//        }
//        return false;
//    }
//
//    private static boolean checkRootMethod3() {
//        Process process = null;
//        try {
//            process = Runtime.getRuntime().exec(new String[] { "/system/xbin/which", "su" });
//            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            return in.readLine() != null;
//        } catch (Throwable t) {
//            return false;
//        } finally {
//            if (process != null) process.destroy();
//        }
//    }

    private static boolean checkRootMethod4() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "su", "-c","whoami"});

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = in.readLine();
            Log.e("RootUtils",output);
            return output.contains("root");
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }
}
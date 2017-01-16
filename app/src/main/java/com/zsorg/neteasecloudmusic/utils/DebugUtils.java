package com.zsorg.neteasecloudmusic.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by piyel_000 on 2017/1/7.
 */

public class DebugUtils {
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info= context.getApplicationInfo();
            return (info.flags& ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {

        }
        return false;
    }
}

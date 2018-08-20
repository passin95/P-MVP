package com.passin.pmvp.util;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.os.Build;
import com.bumptech.glide.util.Synthetic;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/8/20 16:55
 * </pre>
 */
public class AppUtils {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Synthetic
    public static boolean isLowMemoryDevice(ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return activityManager.isLowRamDevice();
        } else {
            return true;
        }
    }

}

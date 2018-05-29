package com.passin.pmvp.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;
import com.passin.pmvp.base.BaseApplication;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/4/27 14:30
 * </pre>
 */
public class NetworkUtils {

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager manager = (ConnectivityManager) BaseApplication.getApp().getSystemService("connectivity");
        return manager == null?null:manager.getActiveNetworkInfo();
    }

}

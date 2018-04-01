package com.passin.pmvp.base.delegate;

import android.app.Application;
import android.content.Context;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/17 10:19
 * </pre>
 */

public interface AppDelegate {
    void attachBaseContext(Context base);

    void onCreate(Application application);

    void onTerminate(Application application);
}

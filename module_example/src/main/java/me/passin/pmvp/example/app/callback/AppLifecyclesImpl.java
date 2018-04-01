package me.passin.pmvp.example.app.callback;

import android.app.Application;
import android.content.Context;

import com.passin.pmvp.base.delegate.AppDelegate;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:10
 * </pre>
 */
public class AppLifecyclesImpl implements AppDelegate{
    @Override
    public void attachBaseContext(Context base) {
    }

    @Override
    public void onCreate(Application application) {
    }

    @Override
    public void onTerminate(Application application) {

    }
}

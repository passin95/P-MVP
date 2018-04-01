package me.passin.pmvp.app;

import com.passin.pmvp.base.BaseApplication;

import me.passin.pmvp.di.component.DaggerAppComponent;


/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/29 22:17
 * </pre>
 */

public class MainApplication extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent
                .builder()
                .armsComponent(getArmsComponent())
                .build()
                .inject(this);
    }
}

package me.passin.pmvp.example.app;

import com.passin.pmvp.base.BaseApplication;

import di.component.DaggerModuleComponent;


/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/22 9:57
 * </pre>
 */
public class ModuleApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerModuleComponent
                .builder()
                .armsComponent(getArmsComponent())
                .build()
                .inject(this);
    }
}

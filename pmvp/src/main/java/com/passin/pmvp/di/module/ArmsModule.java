package com.passin.pmvp.di.module;

import android.app.Application;

import com.passin.pmvp.integration.AppManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/22 9:44
 * </pre>
 */
@Module
public class ArmsModule {
    private Application mApplication;

    public ArmsModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return this.mApplication;
    }


}

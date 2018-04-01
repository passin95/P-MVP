package com.passin.pmvp.di.component;

import com.passin.pmvp.base.delegate.AppDelegateImpl;
import com.passin.pmvp.di.module.AppModule;
import com.passin.pmvp.di.module.ArmsModule;
import com.passin.pmvp.di.module.GlobalConfigModule;
import com.passin.pmvp.di.module.HttpClientModule;
import com.passin.pmvp.http.repository.IRepositoryManager;
import com.passin.pmvp.integration.AppManager;
import com.passin.pmvp.integration.cache.Cache;
import com.passin.pmvp.rxerrorhandler.RxErrorHandler;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/15 10:54
 * </pre>
 */
@Singleton
@Component(modules = {
        AppModule.class,
        HttpClientModule.class,
        GlobalConfigModule.class,
        ArmsModule.class
})
public interface ArmsComponent {

    IRepositoryManager repositoryManager();

    AppManager appManager();

    RxErrorHandler rxErrorHandler();

    Cache<String, Object> extras();

    OkHttpClient okHttpClient();

    File cacheFile();

    Cache.Factory cacheFactory();

    void inject(AppDelegateImpl delegate);
}

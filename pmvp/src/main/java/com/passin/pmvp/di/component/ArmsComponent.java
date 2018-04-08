package com.passin.pmvp.di.component;

import android.app.Application;
import com.passin.pmvp.base.delegate.AppDelegateImpl;
import com.passin.pmvp.di.module.AppModule;
import com.passin.pmvp.di.module.GlobalConfigModule;
import com.passin.pmvp.di.module.HttpClientModule;
import com.passin.pmvp.http.repository.IRepositoryManager;
import com.passin.pmvp.integration.AppManager;
import com.passin.pmvp.integration.cache.Cache;
import com.passin.pmvp.rx.rxerrorhandler.RxErrorHandler;
import dagger.BindsInstance;
import dagger.Component;
import java.io.File;
import java.util.Map;
import javax.inject.Singleton;
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
//        ArmsModule.class
})
public interface ArmsComponent {

    IRepositoryManager repositoryManager();

    AppManager appManager();

    RxErrorHandler rxErrorHandler();

    //存在LruExtras的数据可能被移除，若该数据一定不能为空，请使用extras
    Cache<String, Object> lruExtras();

    Map<String, Object> extras();

    OkHttpClient okHttpClient();

    File cacheFile();

    Cache.Factory cacheFactory();

    void inject(AppDelegateImpl delegate);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        Builder globalConfigModule(GlobalConfigModule globalConfigModule);
        ArmsComponent build();
    }

}

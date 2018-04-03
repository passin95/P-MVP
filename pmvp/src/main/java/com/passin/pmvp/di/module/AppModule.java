package com.passin.pmvp.di.module;

import android.app.Application;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;

import com.passin.pmvp.http.repository.IRepositoryManager;
import com.passin.pmvp.http.repository.RepositoryManager;
import com.passin.pmvp.integration.AppManager;
import com.passin.pmvp.integration.cache.Cache;
import com.passin.pmvp.integration.cache.CacheType;
import com.passin.pmvp.integration.lifecycle.ActivityLifecycle;
import com.passin.pmvp.integration.lifecycle.FragmentLifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:53
 * </pre>
 */
@Module
public abstract class AppModule {

    @Binds
    abstract IRepositoryManager bindRepositoryManager(RepositoryManager repositoryManager);

    @Singleton
    @Provides
    static Cache<String, Object> provideLruExtras(Cache.Factory cacheFactory) {
        return cacheFactory.build(CacheType.EXTRAS);
    }

    @Singleton
    @Provides
    static Map<String, Object> provideExtras() {
        return new ArrayMap<>();
    }

    @Singleton
    @Provides
    static AppManager provideAppManager() {
        return new AppManager();
    }

    @Binds
    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycle(ActivityLifecycle activityLifecycle);

    @Binds
    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(FragmentLifecycle fragmentLifecycle);

    @Singleton
    @Provides
    static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifecycles() {
        return new ArrayList<>();
    }


}

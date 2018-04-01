package com.passin.pmvp.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.Fragment;

import com.passin.pmvp.base.delegate.AppDelegate;
import com.passin.pmvp.base.delegate.AppDelegateImpl;
import com.passin.pmvp.base.delegate.IArms;
import com.passin.pmvp.di.component.ArmsComponent;
import com.passin.pmvp.util.PmvpUtils;
import com.passin.pmvp.util.Preconditions;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/17 10:13
 * </pre>
 */

public class BaseApplication extends MultiDexApplication implements IArms, HasActivityInjector, HasSupportFragmentInjector {

    private static BaseApplication mApplication;

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private AppDelegate mAppDelegate;

    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.mAppDelegate = new AppDelegateImpl(base);
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        this.mAppDelegate.onCreate(this);
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null) {
            this.mAppDelegate.onTerminate(this);
        }
    }

    /**
     * 将 {@link ArmsComponent} 返回出去, 供其它地方使用, {@link ArmsComponent} 接口中声明的方法所返回的实例, 在 {@link #getArmsComponent()} ()} 拿到对象后都可以直接使用
     *
     * @return AppComponent
     * @see PmvpUtils#obtainArmsComponentFromContext(Context) 可直接获取 {@link ArmsComponent}
     */
    @NonNull
    @Override
    public ArmsComponent getArmsComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegateImpl.class.getName());
        Preconditions.checkState(mAppDelegate instanceof IArms, "%s must be implements %s", AppDelegateImpl.class.getName(), IArms.class.getName());
        return ((IArms) mAppDelegate).getArmsComponent();
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }


    public static BaseApplication getApp() {
        return mApplication;
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}

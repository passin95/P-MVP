package com.passin.pmvp.integration;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.passin.pmvp.base.delegate.AppDelegate;
import com.passin.pmvp.di.module.GlobalConfigModule;

import java.util.List;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/20 22:14
 * </pre>
 */

public interface ModuleConfig {
    /**
     * 使用{@link GlobalConfigModule.Builder}给框架配置一些配置参数
     *
     * @param context
     * @param builder
     */
    void applyOptions(Context context, GlobalConfigModule.Builder builder);

    /**
     * 使用{@link AppDelegate}在Application的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    void injectAppLifecycle(Context context, List<AppDelegate> lifecycles);

    /**
     * 使用{@link Application.ActivityLifecycleCallbacks}在Activity的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles);


    /**
     * 使用{@link FragmentManager.FragmentLifecycleCallbacks}在Fragment的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles);
}

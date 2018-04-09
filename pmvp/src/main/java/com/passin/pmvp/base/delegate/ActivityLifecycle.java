package com.passin.pmvp.base.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.passin.pmvp.base.BaseFragment;
import com.passin.pmvp.integration.AppManager;
import com.passin.pmvp.integration.ModuleConfig;
import com.passin.pmvp.integration.cache.Cache;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.AndroidInjection;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/20 21:49
 * </pre>
 */

public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    Cache<String, Object> mExtras;
    @Inject
    Lazy<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycle;
    @Inject
    Lazy<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecycles;

    @Inject
    public ActivityLifecycle() {
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //如果 intent 包含了此字段,并且为 true 说明不加入到 list 进行统一管理
        if (activity.getIntent() == null||!activity.getIntent().getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false)) {
            mAppManager.addActivity(activity);
        }

        //配置ActivityDelegate
        if (activity instanceof IActivity) {
            //是否使用eventBus
            if (((IActivity) activity).useEventBus()) {
                EventBus.getDefault().register(activity);
            }
            //是否使用dagger注入
            if (((IActivity)activity).useInject()) {
                AndroidInjection.inject(activity);
            }
        }

        registerFragmentCallbacks(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mAppManager.removeActivity(activity);
        //如果要使用eventbus请将此方法返回true
        if (activity instanceof IActivity && ((IActivity) activity).useEventBus()) {
            EventBus.getDefault().unregister(activity);
        }
    }

    /**
     * 给每个 Activity 的所有 Fragment 设置监听其生命周期, Activity 可以通过 {@link IActivity#useFragment()}
     * 设置是否使用监听,如果这个 Activity 返回 false 的话,这个 Activity 下面的所有 Fragment 将不能使用 {@link FragmentLifecycle}
     * 意味着 {@link BaseFragment} 也不能使用
     *
     * @param activity
     */
    private void registerFragmentCallbacks(Activity activity) {

        boolean useFragment = activity instanceof IActivity ? ((IActivity) activity).useFragment() : true;
        if (activity instanceof FragmentActivity && useFragment) {

            //mFragmentLifecycle 为 Fragment 生命周期实现类, 用于框架内部对每个 Fragment 的必要操作, 如给每个 Fragment 配置 FragmentDelegate
            //注册框架内部已实现的 Fragment 生命周期逻辑
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle.get(), true);

            if (mExtras.containsKey(ModuleConfig.class.getName())) {
                List<ModuleConfig> modules = (List<ModuleConfig>) mExtras.get(ModuleConfig.class.getName());
                for (ModuleConfig module : modules) {
                    module.injectFragmentLifecycle(mApplication, mFragmentLifecycles.get());
                }
                mExtras.remove(ModuleConfig.class.getName());
            }

            //注册框架外部, 开发者扩展的 Fragment 生命周期逻辑
            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycles.get()) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }
    }


}

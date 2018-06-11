package com.passin.pmvp.base.delegate;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ComponentCallbacks2;
import android.content.ContentProvider;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import com.passin.pmvp.di.component.ArmsComponent;
import com.passin.pmvp.di.component.DaggerArmsComponent;
import com.passin.pmvp.di.module.GlobalConfigModule;
import com.passin.pmvp.integration.ManifestParser;
import com.passin.pmvp.integration.ModuleConfig;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/17 10:20
 * </pre>
 */

public class AppDelegateImpl implements AppDelegate,IArms{

    private Application mApplication;
    private ArmsComponent mArmsComponent;
    private List<ModuleConfig> mModules;
    private List<AppDelegate> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();
    @Inject
    protected ActivityLifecycle mActivityLifecycle;
    private ComponentCallbacks2 mComponentCallback;



    public AppDelegateImpl(Context context) {
        mModules = new ManifestParser(context).parse();

        //遍历之前获得的集合, 执行每一个 ConfigModule 实现类的某些方法
        for (ModuleConfig module : mModules) {

            //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
            module.injectAppLifecycle(context, mAppLifecycles);

            //将框架外部, 开发者实现的 Activity 的生命周期回调 (ActivityLifecycleCallbacks) 存入 mActivityLifecycles 集合 (此时还未注册回调)
            module.injectActivityLifecycle(context, mActivityLifecycles);
        }
    }

    @Override
    public void attachBaseContext(Context base) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
        }
        //遍历 mAppLifecycles, 执行所有已注册的 AppLifecycles 的 attachBaseContext() 方法 (框架外部, 开发者扩展的逻辑)
        for (AppDelegate lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(Application application) {
        this.mApplication = application;

        mArmsComponent = DaggerArmsComponent
                .builder()
                .application(mApplication)
                .globalConfigModule(getGlobalConfigModule(application, mModules))
                .build();
        mArmsComponent.inject(this);

        mArmsComponent.extras().put(ModuleConfig.class.getName(), mModules);
        this.mModules = null;

        for (AppDelegate lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }

        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }

        mComponentCallback = new AppComponentCallbacks(mApplication, mArmsComponent);

        mApplication.registerComponentCallbacks(mComponentCallback);


    }

    @Override
    public void onTerminate(Application application) {
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mComponentCallback != null) {
            mApplication.unregisterComponentCallbacks(mComponentCallback);
        }
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (AppDelegate lifecycle : mAppLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }
        this.mActivityLifecycle = null;
        this.mActivityLifecycles = null;
        this.mComponentCallback = null;
        this.mAppLifecycles = null;
        this.mApplication = null;
    }



    private GlobalConfigModule getGlobalConfigModule(Context context, List<ModuleConfig> modules) {

        GlobalConfigModule.Builder builder = GlobalConfigModule
                .builder();

        //遍历 ConfigModule 集合, 给全局配置 GlobalConfigModule 添加参数
        for (ModuleConfig module : modules) {
            module.applyOptions(context, builder);
        }

        return builder.build();
    }

    @NonNull
    @Override
    public ArmsComponent getArmsComponent() {
        return mArmsComponent;
    }


    private static class AppComponentCallbacks implements ComponentCallbacks2 {
        private Application mApplication;
        private ArmsComponent mArmsComponent;

        public AppComponentCallbacks(Application application, ArmsComponent armsComponent) {
            this.mApplication = application;
            this.mArmsComponent = armsComponent;
        }

        /**
         * 在你的 App 生命周期的任何阶段, {@link ComponentCallbacks2#onTrimMemory(int)} 发生的回调都预示着你设备的内存资源已经开始紧张
         * 你应该根据 {@link ComponentCallbacks2#onTrimMemory(int)} 发生回调时的内存级别来进一步决定释放哪些资源
         * {@link ComponentCallbacks2#onTrimMemory(int)} 的回调可以发生在 {@link Application}、{@link Activity}、{@link Service}、{@link ContentProvider}、{@link Fragment}
         *
         * @see <a href="https://developer.android.com/reference/android/content/ComponentCallbacks2.html#TRIM_MEMORY_RUNNING_MODERATE">level 官方文档</a>
         * @param level 内存级别
         */
        @Override
        public void onTrimMemory(int level) {
            //状态1. 当开发者的 App 正在运行
            //设备开始运行缓慢, 不会被 kill, 也不会被列为可杀死的, 但是设备此时正运行于低内存状态下, 系统开始触发杀死 LRU 列表中的进程的机制
//                case TRIM_MEMORY_RUNNING_MODERATE:


            //设备运行更缓慢了, 不会被 kill, 但请你回收 unused 资源, 以便提升系统的性能, 你应该释放不用的资源用来提升系统性能 (但是这也会直接影响到你的 App 的性能)
//                case TRIM_MEMORY_RUNNING_LOW:


            //设备运行特别慢, 当前 App 还不会被杀死, 但是系统已经把 LRU 列表中的大多数进程都已经杀死, 因此你应该立即释放所有非必须的资源
            //如果系统不能回收到足够的 RAM 数量, 系统将会清除所有的 LRU 列表中的进程, 并且开始杀死那些之前被认为不应该杀死的进程, 例如那个包含了一个运行态 Service 的进程
//                case TRIM_MEMORY_RUNNING_CRITICAL:


            //状态2. 当前 App UI 不再可见, 这是一个回收大个资源的好时机
//                case TRIM_MEMORY_UI_HIDDEN:


            //状态3. 当前的 App 进程被置于 Background LRU 列表中
            //进程位于 LRU 列表的上端, 尽管你的 App 进程并不是处于被杀掉的高危险状态, 但系统可能已经开始杀掉 LRU 列表中的其他进程了
            //你应该释放那些容易恢复的资源, 以便于你的进程可以保留下来, 这样当用户回退到你的 App 的时候才能够迅速恢复
//                case TRIM_MEMORY_BACKGROUND:


            //系统正运行于低内存状态并且你的进程已经已经接近 LRU 列表的中部位置, 如果系统的内存开始变得更加紧张, 你的进程是有可能被杀死的
//                case TRIM_MEMORY_MODERATE:


            //系统正运行与低内存的状态并且你的进程正处于 LRU 列表中最容易被杀掉的位置, 你应该释放任何不影响你的 App 恢复状态的资源
            //低于 API 14 的 App 可以使用 onLowMemory 回调
//                case TRIM_MEMORY_COMPLETE:
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        /**
         * 当系统开始清除 LRU 列表中的进程时, 尽管它会首先按照 LRU 的顺序来清除, 但是它同样会考虑进程的内存使用量, 因此消耗越少的进程则越容易被留下来
         * {@link ComponentCallbacks2#onTrimMemory(int)} 的回调是在 API 14 才被加进来的, 对于老的版本, 你可以使用 {@link ComponentCallbacks2#onLowMemory} 方法来进行兼容
         * {@link ComponentCallbacks2#onLowMemory} 相当于 {@code onTrimMemory(TRIM_MEMORY_COMPLETE)}
         * @see #TRIM_MEMORY_COMPLETE
         */
        @Override
        public void onLowMemory() {
            //系统正运行与低内存的状态并且你的进程正处于 LRU 列表中最容易被杀掉的位置, 你应该释放任何不影响你的 App 恢复状态的资源
        }
    }

}

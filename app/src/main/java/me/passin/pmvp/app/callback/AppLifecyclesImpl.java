package me.passin.pmvp.app.callback;

import android.app.Application;
import android.content.Context;
import butterknife.ButterKnife;
import com.passin.pmvp.base.delegate.AppDelegate;
import com.passin.pmvp.util.PmvpUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import me.passin.pmvp.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;
import timber.log.Timber;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:10
 * </pre>
 */
public class AppLifecyclesImpl implements AppDelegate{
    @Override
    public void attachBaseContext(Context base) {
        // 这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化。
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            MultiDex.install(base);
//        }
    }

    @Override
    public void onCreate(Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        if (BuildConfig.LOG_DEBUG) {
            // Timber初始化
            // Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印，
            // 并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略，
            // 比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器。
            Timber.plant(new Timber.DebugTree());
            // 如果你想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展。
//                    Logger.addLogAdapter(new AndroidLogAdapter());
//                    Timber.plant(new Timber.DebugTree() {
//                        @Override
//                        protected void log(int priority, String tag, String message, Throwable t) {
//                            Logger.log(priority, tag, message, t);
//                        }
//                    });
            ButterKnife.setDebug(true);
        }
        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏，仅在Debug环境生效。
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.LOG_DEBUG)
                /**
                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}，
                 * 在遇到 After onSaveInstanceState 时，不会抛出异常，会回调到下面的ExceptionHandle。
                 */
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();

        // leakCanary 内存泄露检查。
        PmvpUtils.obtainArmsComponentFromContext(application).extras().put(RefWatcher.class.getName(), BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);

    }

    @Override
    public void onTerminate(Application application) {

    }
}

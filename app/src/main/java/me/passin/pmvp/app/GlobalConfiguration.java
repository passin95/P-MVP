package me.passin.pmvp.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import com.passin.pmvp.base.delegate.AppDelegate;
import com.passin.pmvp.di.module.GlobalConfigModule;
import com.passin.pmvp.http.log.RequestInterceptor;
import com.passin.pmvp.integration.ModuleConfig;
import com.passin.pmvp.util.FileUtils;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.passin.pmvp.BuildConfig;
import me.passin.pmvp.app.callback.ActivityLifecycleCallbacksImpl;
import me.passin.pmvp.app.callback.AppLifecyclesImpl;
import me.passin.pmvp.app.callback.FragmentLifecycleCallbacksImpl;
import me.passin.pmvp.app.callback.GlobalHttpHandlerImpl;
import me.passin.pmvp.app.callback.ResponseErrorListenerImpl;
import me.passin.pmvp.data.api.Api;
import okhttp3.Cache;


/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/15 15:18
 * </pre>
 */
public class GlobalConfiguration implements ModuleConfig {

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) { //Release 时,让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }

        builder.baseurl(Api.APP_DOMAIN)
                /**
                 * 专门用于以下格式的数据，并且 data 的数据是 JsonArray 形式，目的是为了减少 log 打印。
                 * dataJsonKey 为服务器提供的 json key 值，例如 data。
                 * 使用该功能则只打印一个 JsonArray 中的一个数据。
                 * public class BaseJson <T>{
                 *     private String code;
                 *     private T data;
                 * }
                 * @return
                 */
//                .dataJsonKey("data")
                // 以下方式是 Arms 框架自带的切换 BaseUrl 的方式, 在整个 App 生命周期内只能切换一次,
                // 若需要无限次的切换 BaseUrl,以及各种复杂的应用场景建议使用 RetrofitUrlManager 框架
                // 以下代码只是配置, 可以使用 OkHttp (AppComponent 中提供) 请求服务器获取到正确的 BaseUrl 后赋值给 GlobalConfiguration
                // .sDomain
                // 切记整个过程必须在第一次调用 Retrofit 接口之前完成, 如果已经调用过 Retrofit 接口, 此种方式将不能切换 BaseUrl
//                .baseurl(new BaseUrl() {
//                    @Override
//                    public HttpUrl url() {
//                        return HttpUrl.parse(sDomain);
//                    }
//                })

                // 可根据当前项目的情况以及环境为框架某些部件提供自定义的缓存策略, 具有强大的扩展性。
//                .cacheFactory(new Cache.Factory() {
//                    @NonNull
//                    @Override
//                    public Cache build(CacheType type) {
//                        switch (type.getCacheTypeId()){
//                            case CacheType.EXTRAS_TYPE_ID:
//                                return new LruCache(1000);
//                            case CacheType.CACHE_SERVICE_CACHE_TYPE_ID:
//                                return new Cache(type.calculateCacheSize(context));//自定义 Cache
//                            default:
//                                return new LruCache(200);
//                        }
//                    }
//                })

                // 若觉得框架默认的打印格式并不能满足自己的需求, 可自行扩展自己理想的打印格式 (以下只是简单实现)。
//                .formatPrinter(new FormatPrinter())

                // 这里提供一个全局处理 Http 请求和响应结果的处理类,可以比客户端提前一步拿到服务器返回的结果。
                // 可以做一些操作,比如 token 超时,重新获取
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                // rxjava 必要要使用 BaseErrorHandleSubscriber,此监听才生效。
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration((context1, gsonBuilder) -> {
                    // 这里可以自己自定义配置 Gson 的参数。
                    gsonBuilder
                            // 支持序列化 null 的参数。
                            .serializeNulls()
                            // 支持将序列化 key 为 object 的 map,默认只能序列化 key 为 string 的 map。
                            .enableComplexMapKeySerialization();
                })
                .retrofitConfiguration((context1, retrofitBuilder) -> {
                    // 这里可以自己自定义配置 Retrofit 的参数,甚至你可以替换框架默认配置的 okhttp 对象。
//                    retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());
                })
                .okhttpConfiguration((context1, okhttpBuilder) -> {
                    // 这里可以自己自定义配置 Okhttp 的参数。
                    okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
                    okhttpBuilder.cache(new Cache(FileUtils.getCacheFile(context), 1024 * 1024 * 30));
                });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppDelegate> appDelegates) {
        // AppLifecycles 的所有方法都会在基类 Application 的对应的生命周期中被调用,
        // 所以在对应的方法中可以扩展一些自己需要的逻辑，可以根据不同的逻辑添加多个实现类
        appDelegates.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context,
            List<Application.ActivityLifecycleCallbacks> lifecycles) {
        // ActivityLifecycleCallbacks 的所有方法都会在 Activity (包括三方库) 的对应的生命周期中被调用,
        // 所以在对应的方法中可以扩展一些自己需要的逻辑，可以根据不同的逻辑添加多个实现类。
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context,
            List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }
}

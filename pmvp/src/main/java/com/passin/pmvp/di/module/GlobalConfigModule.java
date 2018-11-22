package com.passin.pmvp.di.module;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.passin.pmvp.http.BaseUrl;
import com.passin.pmvp.http.GlobalHttpHandler;
import com.passin.pmvp.http.log.DefaultFormatPrinter;
import com.passin.pmvp.http.log.FormatPrinter;
import com.passin.pmvp.http.log.RequestInterceptor;
import com.passin.pmvp.integration.cache.Cache;
import com.passin.pmvp.integration.cache.CacheType;
import com.passin.pmvp.integration.cache.LruCache;
import com.passin.pmvp.rx.rxerrorhandler.BaseErrorHandleSubscriber;
import com.passin.pmvp.rx.rxerrorhandler.ResponseErrorListener;
import com.passin.pmvp.util.FileUtils;
import com.passin.pmvp.util.Preconditions;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.internal.Util;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/17 11:08
 * </pre>
 */

@Module
public class GlobalConfigModule {

    private HttpUrl mApiUrl;
    private BaseUrl mBaseUrl;
    private String dataJsonKey;
    private GlobalHttpHandler mHandler;
    private List<Interceptor> mInterceptors;
    private ResponseErrorListener mErrorListener;
    private File mCacheFile;
    private HttpClientModule.RetrofitConfiguration mRetrofitConfiguration;
    private HttpClientModule.OkhttpConfiguration mOkhttpConfiguration;
    private HttpClientModule.GsonConfiguration mGsonConfiguration;
    private RequestInterceptor.Level mPrintHttpLogLevel;
    private FormatPrinter mFormatPrinter;
    private Cache.Factory mCacheFactory;
    private ExecutorService mExecutorService;


    private GlobalConfigModule(Builder builder) {
        this.mApiUrl = builder.apiUrl;
        this.mBaseUrl = builder.baseUrl;
        this.dataJsonKey = builder.dataJsonKey;
        this.mHandler = builder.handler;
        this.mInterceptors = builder.interceptors;
        this.mErrorListener = builder.responseErrorListener;
        this.mCacheFile = builder.cacheFile;
        this.mRetrofitConfiguration = builder.retrofitConfiguration;
        this.mOkhttpConfiguration = builder.okhttpConfiguration;
        this.mGsonConfiguration = builder.gsonConfiguration;
        this.mPrintHttpLogLevel = builder.printHttpLogLevel;
        this.mFormatPrinter = builder.formatPrinter;
        this.mCacheFactory = builder.cacheFactory;
        this.mExecutorService = builder.executorService;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideInterceptors() {
        return mInterceptors;
    }

    /**
     * 提供 BaseUrl,默认使用 <"https://api.github.com/">。
     *
     * @return
     */
    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        if (mBaseUrl != null) {
            HttpUrl httpUrl = mBaseUrl.url();
            if (httpUrl != null) {
                return httpUrl;
            }
        }
        return mApiUrl == null ? HttpUrl.parse("https://api.github.com/") : mApiUrl;
    }

    /**
     * 提供处理 Http 请求和响应结果的处理类。
     *
     * @return
     */
    @Singleton
    @Provides
    @Nullable
    GlobalHttpHandler provideGlobalHttpHandler() {
        return mHandler;
    }

    /**
     * 提供缓存文件夹。
     */
    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        return mCacheFile == null ? FileUtils.getCacheFile(application) : mCacheFile;
    }

    /**
     * 提供处理 RxJava 错误的管理器的回调
     *
     * @return
     */
    @Singleton
    @Provides
    ResponseErrorListener provideResponseErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }

    @Singleton
    @Provides
    @Nullable
    HttpClientModule.RetrofitConfiguration provideRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    HttpClientModule.OkhttpConfiguration provideOkhttpConfiguration() {
        return mOkhttpConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    HttpClientModule.GsonConfiguration provideGsonConfiguration() {
        return mGsonConfiguration;
    }

    @Singleton
    @Provides
    RequestInterceptor.Level providePrintHttpLogLevel() {
        return mPrintHttpLogLevel == null ? RequestInterceptor.Level.ALL : mPrintHttpLogLevel;
    }


    /**
     * 提供日志输出方式。可添加 dataJsonKey，当 data 格式为 {@link com.google.gson.JsonArray}时，只输出一组数据。
     * 当网络请求 Json 格式为 Demo 中 BaseJson 样式，并且 data 的键值为 dataJsonKey 生效。
     *
     * @return
     */
    @Singleton
    @Provides
    FormatPrinter provideFormatPrinter(){
        return mFormatPrinter == null ? new DefaultFormatPrinter(dataJsonKey) : mFormatPrinter;
    }


    @Singleton
    @Provides
    Cache.Factory provideCacheFactory(final Application application) {
        return mCacheFactory == null ? new Cache.Factory() {
            @NonNull
            @Override
            public Cache build(CacheType type) {
                // 若想自定义 LruCache 的 size, 或者不想使用 LruCache, 想使用自己自定义的策略，
                // 结合 GlobalConfigModule.Builder#cacheFactory() 使用和扩展。
                return new LruCache(type.calculateCacheSize(application));
            }
        } : mCacheFactory;
    }

    /**
     * 返回一个全局公用的线程池,适用于大多数异步需求。
     * 避免多个线程池创建带来的资源消耗。
     *
     * @return {@link ExecutorService}
     */
    @Singleton
    @Provides
    ExecutorService provideExecutorService() {
        return mExecutorService == null ? new ThreadPoolExecutor(1, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new SynchronousQueue<>(), Util.threadFactory("Pmvp Executor", false)) : mExecutorService;
    }

    public static final class Builder {
        private HttpUrl apiUrl;
        private BaseUrl baseUrl;
        private String dataJsonKey;
        private GlobalHttpHandler handler;
        private List<Interceptor> interceptors;
        private ResponseErrorListener responseErrorListener;
        private File cacheFile;
        private HttpClientModule.RetrofitConfiguration retrofitConfiguration;
        private HttpClientModule.OkhttpConfiguration okhttpConfiguration;
        private HttpClientModule.GsonConfiguration gsonConfiguration;
        private RequestInterceptor.Level printHttpLogLevel;
        private FormatPrinter formatPrinter;
        private Cache.Factory cacheFactory;
        private ExecutorService executorService;

        private Builder() {
        }

        public Builder baseurl(String baseUrl) {
            if (TextUtils.isEmpty(baseUrl)) {
                throw new NullPointerException("BaseUrl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseurl(BaseUrl baseUrl) {
            this.baseUrl = Preconditions.checkNotNull(baseUrl, BaseUrl.class.getCanonicalName() + "can not be null.");
            return this;
        }

        public Builder dataJsonKey(String keyName) {
            this.dataJsonKey = Preconditions.checkNotNull(keyName, "keyName can not be null.");
            return this;
        }

        /**
         * 用来处理 Http 全局响应结果。
         * @param handler
         * @return
         */
        public Builder globalHttpHandler(GlobalHttpHandler handler) {
            this.handler = handler;
            return this;
        }

        /**
         * 为 OkHttp 添加 Interceptor，该拦截器在请求构建会后于框架提供的 {@link GlobalHttpHandler}执行。
         * @param interceptor
         * @return
         */
        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptors == null) {
                interceptors = new ArrayList<>();
            }
            this.interceptors.add(interceptor);
            return this;
        }

        /**
         * 共同处理所有使用 {@link BaseErrorHandleSubscriber} 的 RxJava的onError()逻辑。
         * @param listener
         * @return
         */
        public Builder responseErrorListener(ResponseErrorListener listener) {
            this.responseErrorListener = listener;
            return this;
        }

        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        public Builder retrofitConfiguration(HttpClientModule.RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okhttpConfiguration(HttpClientModule.OkhttpConfiguration okhttpConfiguration) {
            this.okhttpConfiguration = okhttpConfiguration;
            return this;
        }

        public Builder gsonConfiguration(HttpClientModule.GsonConfiguration gsonConfiguration) {
            this.gsonConfiguration = gsonConfiguration;
            return this;
        }

        /**
         * 控制框架默认 Http 请求输出的日志级别。
         * @param printHttpLogLevel 日志级别。
         * @return
         */
        public Builder printHttpLogLevel(RequestInterceptor.Level printHttpLogLevel) {
            this.printHttpLogLevel = Preconditions.checkNotNull(printHttpLogLevel, "The printHttpLogLevel can not be null, use RequestInterceptor.Level.NONE instead.");
            return this;
        }

        public Builder formatPrinter(FormatPrinter formatPrinter){
            this.formatPrinter = Preconditions.checkNotNull(formatPrinter, FormatPrinter.class.getCanonicalName() + "can not be null.");
            return this;
        }

        public Builder cacheFactory(Cache.Factory cacheFactory) {
            this.cacheFactory = cacheFactory;
            return this;
        }

        public Builder executorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }

    }

}

package com.passin.pmvp.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.passin.pmvp.http.GlobalHttpHandler;
import com.passin.pmvp.http.log.RequestInterceptor;
import com.passin.pmvp.rxerrorhandler.ResponseErrorListener;
import com.passin.pmvp.rxerrorhandler.RxErrorHandler;
import com.passin.pmvp.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/15 11:41
 * </pre>
 */
@Module
public abstract class HttpClientModule {

    private static final int TIME_OUT = 10;


    @Singleton
    @Provides
    static Retrofit provideRetrofit(Application application, @Nullable RetrofitConfiguration configuration, Retrofit.Builder builder, OkHttpClient client
            , HttpUrl httpUrl, Gson gson) {
        builder
                .baseUrl(httpUrl)
                .client(client);

        if (configuration != null) {
            configuration.configRetrofit(application, builder);
        }

        /**
         *   加在configuration之后的原因是Retrofit将会按照添加不同解析方式的顺序按先添加先“尝试”解析的方式进行解析
         *   即按照
         *   builder.addConverterFactory(FastJsonConverterFactory.create());//比如使用fastjson替代gson
         *     .addConverterFactory(GsonConverterFactory.create(gson));
         *   的顺序添加时，优先采取fastjson解析，当fastjson解析失败后再去尝试gson解析
         */
        builder
                ////使用 Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //使用 Gson
                .addConverterFactory(GsonConverterFactory.create(gson));
        return builder.build();
    }


    @Singleton
    @Provides
    static OkHttpClient provideClient(Application application, @Nullable OkhttpConfiguration configuration, OkHttpClient.Builder builder, Interceptor intercept
            , @Nullable List<Interceptor> interceptors, @Nullable final GlobalHttpHandler handler) {
        builder
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept);

        if (handler != null) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()));
                }
            });
        }

        //如果外部提供了interceptor的集合则遍历添加
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (configuration != null) {
            configuration.configOkhttp(application, builder);
        }
        return builder.build();
    }


    @Singleton
    @Provides
    static Gson provideGson(Application application, @Nullable GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null) {
            configuration.configGson(application, builder);
        }
        return builder.create();
    }


    @Singleton
    @Provides
    static RxCache provideRxCache(Application application, @Nullable RxCacheConfiguration configuration, @Named("RxCacheDirectory") File cacheDirectory) {
        RxCache.Builder builder = new RxCache.Builder();
        RxCache rxCache = null;
        if (configuration != null) {
            rxCache = configuration.configRxCache(application, builder);
        }
        if (rxCache != null) {
            return rxCache;
        } else {
            return builder
                    .persistence(cacheDirectory, new GsonSpeaker());
        }
    }


    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    static File provideRxCacheDirectory(File cacheDir) {
        File cacheDirectory = new File(cacheDir, "RxCache");
        return FileUtils.makeDirs(cacheDirectory);
    }

    @Binds
    abstract Interceptor bindInterceptor(RequestInterceptor interceptor);


    @Singleton
    @Provides
    static RxErrorHandler proRxErrorHandler(Application application, ResponseErrorListener listener) {
        return RxErrorHandler
                .builder()
                .with(application)
                .responseErrorListener(listener)
                .build();
    }


    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    static OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }




    public interface RetrofitConfiguration {
        /**
         * 提供接口，可在GlobalConfiguration全局自定义配置 Retrofit
         *
         * @param context Context
         * @param builder Retrofit.Builder
         */
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    public interface OkhttpConfiguration {
        /**
         * 提供接口，可在GlobalConfiguration全局自定义配置 OkHttpClient
         *
         * @param context Context
         * @param builder OkHttpClient.Builder
         */
        void configOkhttp(Context context, OkHttpClient.Builder builder);
    }

    public interface GsonConfiguration {
        /**
         * 提供接口，可在GlobalConfiguration全局自定义配置 Gson
         *
         * @param context Context
         * @param builder GsonBuilder
         */
        void configGson(Context context, GsonBuilder builder);
    }

    public interface RxCacheConfiguration {
        /**
         * 提供接口，自定义配置 RxCache
         * 若想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 fastjson
         * 请 {@code return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());},
         * 否则请 {@code return null;}
         *
         * @param context Context
         * @param builder RxCache.Builder
         * @return RxCache
         */
        RxCache configRxCache(Context context, RxCache.Builder builder);
    }

}

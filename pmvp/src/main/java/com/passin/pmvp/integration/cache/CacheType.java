package com.passin.pmvp.integration.cache;

import android.app.ActivityManager;
import android.content.Context;
import com.passin.pmvp.di.component.ArmsComponent;
import com.passin.pmvp.http.repository.RepositoryManager;
import com.passin.pmvp.util.AppUtils;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/20 21:33
 * </pre>
 */

public interface CacheType {
    int RETROFIT_SERVICE_CACHE_TYPE_ID = 0;
    int EXTRAS_TYPE_ID = 1;

    /**
     * {@link RepositoryManager}中存储 Retrofit Service 的容器
     */
    CacheType RETROFIT_SERVICE_CACHE = new CacheType() {
        private static final int MAX_SIZE = 70;

        @Override
        public int getCacheTypeId() {
            return RETROFIT_SERVICE_CACHE_TYPE_ID;
        }

        @Override
        public int calculateCacheSize(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            int targetMemoryCacheSize;
            if (AppUtils.isLowMemoryDevice(activityManager)) {
                targetMemoryCacheSize = activityManager.getMemoryClass() / 6;
            } else {
                targetMemoryCacheSize = activityManager.getMemoryClass() / 4;
            }
            if (targetMemoryCacheSize >= MAX_SIZE) {
                return MAX_SIZE;
            }
            return targetMemoryCacheSize;
        }
    };


    /**
     * {@link ArmsComponent} 中的 lruExtras
     */
    CacheType LRU_EXTRAS = new CacheType() {
        private static final int MAX_SIZE = 100;

        @Override
        public int getCacheTypeId() {
            return EXTRAS_TYPE_ID;
        }

        @Override
        public int calculateCacheSize(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            int targetMemoryCacheSize;
            if (AppUtils.isLowMemoryDevice(activityManager)) {
                targetMemoryCacheSize = activityManager.getMemoryClass() / 5;
            } else {
                targetMemoryCacheSize = activityManager.getMemoryClass() / 3;
            }
            if (targetMemoryCacheSize >= MAX_SIZE) {
                return MAX_SIZE;
            }
            return targetMemoryCacheSize;
        }
    };


    /**
     * 返回框架内需要缓存的模块对应的 {@code id}
     *
     * @return
     */
    int getCacheTypeId();

    /**
     * 计算对应模块需要的缓存大小
     *
     * @return
     */
    int calculateCacheSize(Context context);
}

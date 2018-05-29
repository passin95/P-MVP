package com.passin.pmvp.http.repository;

import com.passin.pmvp.integration.cache.Cache;
import com.passin.pmvp.integration.cache.CacheType;
import com.passin.pmvp.util.Preconditions;
import dagger.Lazy;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Retrofit;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/19 14:42
 * </pre>
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {
    @Inject
    Lazy<Retrofit> mRetrofit;
    @Inject
    Cache.Factory mCachefactory;

    private Cache<String, Object> mRetrofitServiceCache;

    @Inject
    public RepositoryManager() {
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service
     * @param <T>
     * @return
     */
    @Override
    public synchronized <T> T obtainRetrofitService(Class<T> service) {
        if (mRetrofitServiceCache == null)
            mRetrofitServiceCache = mCachefactory.build(CacheType.RETROFIT_SERVICE_CACHE);
        Preconditions.checkNotNull(mRetrofitServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService = (T) mRetrofitServiceCache.get(service.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = mRetrofit.get().create(service);
            mRetrofitServiceCache.put(service.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

}

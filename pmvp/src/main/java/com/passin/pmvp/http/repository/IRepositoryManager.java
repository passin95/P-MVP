package com.passin.pmvp.http.repository;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/14 10:04
 * </pre>
 */
public interface IRepositoryManager {

    /**
     * 根据传入的 Class 获取对应的 Retrofit Service。
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);

}

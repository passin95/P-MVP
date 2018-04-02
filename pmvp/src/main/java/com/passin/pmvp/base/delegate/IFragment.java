package com.passin.pmvp.base.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passin.pmvp.integration.cache.Cache;
import com.passin.pmvp.integration.cache.LruCache;

import org.greenrobot.eventbus.EventBus;


/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/20 23:26
 * </pre>
 */

public interface IFragment {

    /**
     * 提供在 {@link Fragment} 生命周期内的缓存容器, 可向此 {@link Fragment} 存取一些必要的数据
     * 此缓存容器和 {@link Fragment} 的生命周期绑定, 如果 {@link Fragment} 在屏幕旋转或者配置更改的情况下
     * 重新创建, 那此缓存容器中的数据也会被清空
     *
     * @return like {@link LruCache}
     */
    @NonNull
    Cache<String, Object> provideCache();


    /**
     * 是否使用 {@link EventBus}
     *
     * @return
     */
    boolean useEventBus();

    /**
     * 是否使用Daggerr注入
     *
     * @return
     */
    boolean useInject();

    /**
     * 初始化 View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);
}

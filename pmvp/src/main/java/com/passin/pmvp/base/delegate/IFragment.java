package com.passin.pmvp.base.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
     * 是否使用 {@link EventBus}。
     *
     * @return
     */
    boolean useEventBus();

    /**
     * 是否使用 Dagger 注入。
     *
     * @return
     */
    boolean useInject();

    /**
     * 初始化 View。
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化数据。
     *
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);

}

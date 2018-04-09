package com.passin.pmvp.base.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.passin.pmvp.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;


/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/18 21:49
 * </pre>
 */

public interface IActivity {


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
     * 初始化 View,如果initView返回0,框架则不会调用{@link Activity#setContentView(int)}
     *
     * @param savedInstanceState
     * @return
     */
    int initView(Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(Bundle savedInstanceState);

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    boolean useFragment();


}

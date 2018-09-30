package com.passin.pmvp.base;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import dagger.internal.Preconditions;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import org.greenrobot.eventbus.EventBus;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/13 21:46
 * </pre>
 */

public class BasePresenter<V extends IView> implements LifecycleObserver {

    protected CompositeDisposable mCompositeDisposable;

    protected Context mContext;

    protected V mRootView;


    public BasePresenter(V rootView) {
        this.mRootView = Preconditions.checkNotNull(rootView,"%s cannot be null", IView.class.getName());
        // 提供 Context。
        if (rootView instanceof Activity) {
            mContext = (Activity) rootView;
        } else if (rootView instanceof Fragment) {
            mContext = ((Fragment) rootView).getContext();
        } else if (rootView instanceof View) {
            mContext = ((View) rootView).getContext();
        }
        onStart();
    }


    public void onStart() {
        if (mRootView != null && mRootView instanceof LifecycleOwner) {
            ((LifecycleOwner) mRootView).getLifecycle().addObserver(this);
        }
        // 如果要使用 Eventbus 请将此方法返回 true。
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }


    /**
     * 绑定生命周期组件此处绑定了 activity(fragment) onDestroy()。
     * 如果在某个界面有对其它生命周期的需求，请自行根据需求绑定。
     * @param owner
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner) {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        // 生命周期结束时取消所有正在执行的订阅
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        owner.getLifecycle().removeObserver(this);
    }

    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理，
     * 根据自己的需求在适当时期停止正在执行的 RxJava 任务,避免内存泄漏。
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 是否使用 {@link EventBus},默认为不使用 (false)，
     * 如果 true，必须真的接收某个事件。
     *
     * @return
     */
    public boolean useEventBus() {
        return false;
    }
}

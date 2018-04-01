package com.passin.pmvp.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import org.greenrobot.eventbus.EventBus;

import dagger.internal.Preconditions;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/13 21:46
 * </pre>
 */

public class BasePresenter<V extends IView> implements LifecycleObserver {

    protected CompositeDisposable mCompositeDisposable;

    protected V mRootView;


    public BasePresenter(V rootView) {
        this.mRootView = Preconditions.checkNotNull(rootView,"%s cannot be null", IView.class.getName());
        initData();
    }


    public void initData() {
        mCompositeDisposable = new CompositeDisposable();

        if (mRootView!=null&&mRootView instanceof LifecycleOwner) {
            ((LifecycleOwner)mRootView).getLifecycle().addObserver(this);
        }
        //如果要使用 Eventbus 请将此方法返回 true
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }


    /**
     * 绑定生命周期组件此处绑定了activity(fragment)onDestroy()的时候
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
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 根据自己的需求在适当时期停止正在执行的 RxJava 任务,避免内存泄漏
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入集中处理
    }

    /**
     * 是否使用{@link EventBus},默认为不使用(false)，
     * 如果true，必须真的接收某个事件
     *
     * @return
     */
    public boolean useEventBus() {
        return false;
    }
}

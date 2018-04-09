package com.passin.pmvp.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 9:21
 * </pre>
 */
@Singleton
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    @Inject
    public FragmentLifecycle() {
    }

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        Timber.w(f.toString() + " - onFragmentAttached");
        if (f instanceof IFragment && ((IFragment) f).useInject()) {
            AndroidSupportInjection.inject(f);
        }
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Timber.w(f.toString() + " - onFragmentCreated");
        if (f instanceof IFragment && ((IFragment) f).useEventBus()) {
            EventBus.getDefault().register(f);
        }
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        Timber.w(f.toString() + " - onFragmentViewCreated");
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Timber.w(f.toString() + " - onFragmentActivityCreated");
        if (f instanceof IFragment) {
            ((IFragment)f).initData(savedInstanceState);
        }
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentStarted");
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentResumed");
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentPaused");
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentStopped");
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        Timber.w(f.toString() + " - onFragmentSaveInstanceState");
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentViewDestroyed");
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentDestroyed");
        if (f instanceof IFragment && ((IFragment) f).useEventBus()) {
            EventBus.getDefault().unregister(f);//注册到事件主线
        }
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        Timber.w(f.toString() + " - onFragmentDetached");
    }


}
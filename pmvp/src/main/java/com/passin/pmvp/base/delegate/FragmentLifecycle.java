package com.passin.pmvp.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import dagger.android.support.AndroidSupportInjection;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;

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
        if (f instanceof IFragment && ((IFragment) f).useInject()) {
            AndroidSupportInjection.inject(f);
        }
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        if (f instanceof IFragment && ((IFragment) f).useEventBus()) {
            EventBus.getDefault().register(f);
        }
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        if (f instanceof IFragment) {
            ((IFragment)f).initData(savedInstanceState);
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        if (f instanceof IFragment && ((IFragment) f).useEventBus()) {
            EventBus.getDefault().unregister(f);
        }
    }

}
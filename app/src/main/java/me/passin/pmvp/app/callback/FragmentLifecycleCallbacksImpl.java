package me.passin.pmvp.app.callback;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;
import android.view.View;
import com.passin.pmvp.util.PmvpUtils;
import com.squareup.leakcanary.RefWatcher;
import timber.log.Timber;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 10:30
 * </pre>
 */
public class FragmentLifecycleCallbacksImpl extends FragmentLifecycleCallbacks {

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        Timber.d(f.toString() + " - onFragmentAttached");
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Timber.d(f.toString() + " - onFragmentCreated");
        // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建时重复利用已经创建的 Fragment。
        // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
        // 如果在 XML 中使用 <Fragment/> 标签,的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则 setRetainInstance(true) 无效
        // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
        f.setRetainInstance(true);
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        Timber.d(f.toString() + " - onFragmentViewCreated");
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        Timber.d(f.toString() + " - onFragmentActivityCreated");
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        Timber.d(f.toString() + " - onFragmentStarted");
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        Timber.d(f.toString() + " - onFragmentResumed");
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        Timber.d(f.toString() + " - onFragmentPaused");
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        Timber.d(f.toString() + " - onFragmentStopped");
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        Timber.d(f.toString() + " - onFragmentSaveInstanceState");
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        Timber.d(f.toString() + " - onFragmentViewDestroyed");
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        Timber.d(f.toString() + " - onFragmentDestroyed");
        ((RefWatcher) PmvpUtils
                .obtainArmsComponentFromContext(f.getActivity())
                .extras()
                .get(RefWatcher.class.getName()))
                .watch(f);
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        Timber.d(f.toString() + " - onFragmentDetached");
    }
}
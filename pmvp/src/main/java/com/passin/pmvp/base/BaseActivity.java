package com.passin.pmvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.passin.pmvp.base.delegate.IActivity;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.SupportHelper;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import org.greenrobot.eventbus.EventBus;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/14 10:28
 * =========================================
 * 此处暂时直接继承 BasePresenter，如果你的需求有多个 BasePresenter，
 * 请抽取这些 P 层的共性作为接口 IPresenter 的方法，并将泛型改为继承 IPresenter。
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity implements
        IActivity, ISupportActivity, HasSupportFragmentInjector {

    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);
    protected CompositeDisposable mCompositeDisposable;

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);

        try {
            int layoutResID = initView(savedInstanceState);
            // 如果 initView 返回 0,框架则不会调用 setContentView(),当然也不会 Bind ButterKnife。
            if (layoutResID != 0) {
                setContentView(layoutResID);
                // 绑定到 ButterKnife。
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }

    /**
     * 是否使用 {@link EventBus},默认为不使用 (false)，
     * 如果为 true，必须接收某个事件。
     */
    @Override
    public boolean useEventBus() {
        return false;
    }

    /**
     * 是否使用 Dagger 注入,默认为使用 (true)。
     */
    @Override
    public boolean useInject() {
        return true;
    }

    /**
     * 该 Activity 是否会使用 Fragment，框架会根据这个属性判断是否注册 {@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回 false，那意味着该 Activity 不需要使用 Fragment，
     * 那你再在这个 Activity 中绑定继承于 {@link BaseFragment} 的 Fragment 将不起任何作用。
     */
    @Override
    public boolean useFragment() {
        return false;
    }

    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 根据自己的需求在适当时期停止正在执行的 RxJava 任务,避免内存泄漏
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        // 将所有 Disposable 放入 CompositeDisposable 集中处理。
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return this.mFragmentInjector;
    }

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return mDelegate;
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义 Tag，添加 SharedElement 动画，操作非回退栈 Fragment。
     */
    @Override
    public ExtraTransaction extraTransaction() {
        return mDelegate.extraTransaction();
    }

    /**
     * 获取设置的全局动画 copy。
     *
     * @return FragmentAnimator
     */
    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mDelegate.getFragmentAnimator();
    }

    /**
     * Set all fragments animation.
     * 设置 Fragment 内的全局动画。
     */
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    /**
     * Set all fragments animation.
     * 构建 Fragment 转场动画。
     * <p/>
     * 如果是在 Activity 内实现,则构建的是 Activity 内所有 Fragment 的转场动画，
     * 如果是在 Fragment 内实现,则构建的是该 Fragment 的转场动画,此时优先级 > Activity 的 onCreateFragmentAnimator()。
     *
     * @return FragmentAnimator 对象
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mDelegate.onCreateFragmentAnimator();
    }

    /**
     * Causes the Runnable r to be added to the action queue.
     * <p>
     * The runnable will be run after all the previous action has been run.
     * <p>
     * 前面的事务全部执行后执行该 Action。
     */
    @Override
    public void post(Runnable runnable) {
        mDelegate.post(runnable);
    }

    /**
     * 该方法回调时机为,Activity 回退栈内 Fragment 的数量 小于等于 1 时,默认 finish Activity。
     * 请尽量复写该方法,避免复写 onBackPress(),以保证 SupportFragment 内的 onBackPressedSupport() 回退事件正常执行。
     */
    @Override
    public void onBackPressedSupport() {
        mDelegate.onBackPressedSupport();
    }

    /**
     * Note： return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mDelegate == null) {
            super.dispatchTouchEvent(ev);
        }
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    /**
     * 不建议复写该方法,请使用 {@link #onBackPressedSupport} 代替。
     */
    @Override
    final public void onBackPressed() {
        mDelegate.onBackPressed();
    }


    /****************************************以下为可选方法 (Optional methods)******************************************************/

    // 选择性拓展其他方法
    public void loadRootFragment(int containerId, @NonNull ISupportFragment toFragment) {
        mDelegate.loadRootFragment(containerId, toFragment);
    }

    public void start(ISupportFragment toFragment) {
        mDelegate.start(toFragment);
    }

    /**
     * @param launchMode Same as Activity's LaunchMode.
     */
    public void start(ISupportFragment toFragment, @ISupportFragment.LaunchMode int launchMode) {
        mDelegate.start(toFragment, launchMode);
    }

    /**
     * It is recommended to use {@link BaseFragment#startWithPopTo(ISupportFragment, Class,
     * boolean)}.
     *
     * @see #popTo(Class, boolean) +
     * @see #start(ISupportFragment)
     */
    public void startWithPopTo(ISupportFragment toFragment, Class<?> targetFragmentClass,
            boolean includeTargetFragment) {
        mDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
    }

    /**
     * Pop the Fragment.
     */
    public void pop() {
        mDelegate.pop();
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment);
    }

    /**
     * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
     * 如果你想在出栈后, 立刻进行 FragmentTransaction 操作，请使用该方法。
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment,
            Runnable afterPopTransactionRunnable) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
    }

    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment,
            Runnable afterPopTransactionRunnable, int popAnim) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable,
                popAnim);
    }

    /**
     * 得到位于栈顶 Fragment。
     */
    public ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getSupportFragmentManager());
    }

    /**
     * 获取栈内的 Fragment 对象。
     */
    public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
        return SupportHelper.findFragment(getSupportFragmentManager(), fragmentClass);
    }
}

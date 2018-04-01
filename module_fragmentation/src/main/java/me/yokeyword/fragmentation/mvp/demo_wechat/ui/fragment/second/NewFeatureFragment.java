package me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.second;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.R2;
import me.yokeyword.fragmentation.mvp.demo_wechat.base.NoInjectFragment;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.CycleFragment;


/**
 * 该类是展示 1.0 版本新特性：拓展事务 extraTransaction()
 * <p>
 * Created by YoKey on 16/11/25.
 */
public class NewFeatureFragment extends NoInjectFragment {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    public static NewFeatureFragment newInstance() {
        Bundle args = new Bundle();
        NewFeatureFragment fragment = new NewFeatureFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_wechat_fragment_new_feature, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar.setTitle("NewFeatures");
    }


    @OnClick(R2.id.btn_start_dont_hide)
    public void startDontHide() {
        // 自定义动画启动一个Fragment，并且不隐藏自己
        extraTransaction()
                .setCustomAnimations(R.anim.v_fragment_enter, 0, 0, R.anim.v_fragment_exit)
                .startDontHideSelf(ViewFragment.newInstance());
    }

    @OnClick(R2.id.btn_start)
    public void onClick() {
        // 自定义动画启动一个Fragment
        extraTransaction()
//                        .setTag("CustomTag")
//                        . ...
                .setCustomAnimations(R.anim.v_fragment_enter, R.anim.v_fragment_pop_exit,
                        R.anim.v_fragment_pop_enter, R.anim.v_fragment_exit)
                .start(CycleFragment.newInstance(0));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
    }

}

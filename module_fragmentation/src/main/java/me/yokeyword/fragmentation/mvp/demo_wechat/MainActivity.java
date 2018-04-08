package me.yokeyword.fragmentation.mvp.demo_wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.passin.pmvp.base.BaseActivity;
import com.passin.pmvp.base.IView;
import com.passin.pmvp.rx.rxerrorhandler.RxErrorHandler;

import javax.inject.Inject;

import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.MainFragment;

/**
 * 仿微信交互方式Demo
 * Created by YoKeyword on 16/6/30.
 */
public class MainActivity extends BaseActivity<MainPresenter> implements IView {

    //可打断点测试是否在APP单例
    @Inject
    RxErrorHandler mErrorHandler;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragmentation_wechat_activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }


    @Override
    public void showLoading(@NonNull String content) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void killMyself() {

    }

}

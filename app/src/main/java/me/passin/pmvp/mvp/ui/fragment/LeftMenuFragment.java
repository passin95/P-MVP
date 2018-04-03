package me.passin.pmvp.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passin.pmvp.base.BaseFragment;

import me.passin.pmvp.R;
import me.passin.pmvp.mvp.presenter.LeftMenuPresenter;
import me.passin.pmvp.mvp.IView.LeftMenuView;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/23 23:46
 * </pre>
 */

public class LeftMenuFragment extends BaseFragment<LeftMenuPresenter> implements LeftMenuView {



    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_left_menu, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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

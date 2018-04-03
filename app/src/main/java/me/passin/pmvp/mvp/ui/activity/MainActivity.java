package me.passin.pmvp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.passin.pmvp.base.BaseActivity;
import com.passin.pmvp.rxerrorhandler.RxErrorHandler;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.passin.pmvp.R;
import me.passin.pmvp.globalconfig.constant.ComponentActionConstant;
import me.passin.pmvp.globalconfig.constant.ComponentNameConstant;
import me.passin.pmvp.globalconfig.utils.JsonFormat;
import me.passin.pmvp.mvp.presenter.MainPresenter;
import me.passin.pmvp.mvp.IView.MainView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {


    @BindView(R.id.tv_cc_info)
    TextView mTvCcInfo;

    @Inject
    RxErrorHandler mErrorHandler;


    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.home_activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @OnClick({R.id.iv_example, R.id.iv_fragmentation})
    public void onClick(View view) {
        CC cc = null;
        CCResult result = null;
        switch (view.getId()) {
            case R.id.iv_example:
                cc = CC.obtainBuilder(ComponentNameConstant.MODULE_EXAMPLE)
                        .setActionName(ComponentActionConstant.SHOW_HOME_ACTIVITY)
                        .build();
                result = cc.call();
                //同步调用才可使用界面跳转动画
                overridePendingTransition(R.anim.v_fragment_enter, R.anim.v_fragment_exit);
                break;
            case R.id.iv_fragmentation:
                CC.obtainBuilder(ComponentNameConstant.MODULE_FRAGMENTATION)
                        .setActionName(ComponentActionConstant.SHOW_HOME_ACTIVITY)
                        .build().callAsyncCallbackOnMainThread(new IComponentCallback() {
                    @Override
                    public void onResult(CC cc, CCResult result) {
                        showResult(cc, result);
                    }
                });
                break;
        }
        if (cc != null && result != null) {
            showResult(cc, result);
        }
    }


    private void showResult(CC cc, CCResult result) {
        String text = "result:\n" + JsonFormat.format(result.toString());
        text += "\n\n---------------------\n\n";
        text += "cc:\n" + JsonFormat.format(cc.toString());
        text += "\nresult："+result.getDataMap().get("cc");
        mTvCcInfo.setText(text);
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

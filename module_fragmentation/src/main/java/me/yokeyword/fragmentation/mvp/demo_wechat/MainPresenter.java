package me.yokeyword.fragmentation.mvp.demo_wechat;

import android.arch.lifecycle.LifecycleOwner;

import com.passin.pmvp.base.BasePresenter;
import com.passin.pmvp.base.IView;
import com.passin.pmvp.di.scope.PageScope;
import com.passin.pmvp.rxerrorhandler.RxErrorHandler;

import javax.inject.Inject;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:43
 * </pre>
 */

@PageScope
public class MainPresenter extends BasePresenter<IView>{


    //可打断点测试是否在APP单例
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MainPresenter(IView rootView) {
        super(rootView);
    }


    @Override
    public void onDestroy(LifecycleOwner owner) {
        super.onDestroy(owner);
    }
}

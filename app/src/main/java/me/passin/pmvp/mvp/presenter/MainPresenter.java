package me.passin.pmvp.mvp.presenter;

import android.arch.lifecycle.LifecycleOwner;

import com.passin.pmvp.base.BasePresenter;
import com.passin.pmvp.di.scope.PageScope;
import com.passin.pmvp.rx.rxerrorhandler.RxErrorHandler;

import javax.inject.Inject;

import me.passin.pmvp.mvp.IView.MainView;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:43
 * </pre>
 */

@PageScope
public class MainPresenter extends BasePresenter<MainView>{

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MainPresenter(MainView rootView) {
        super(rootView);
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        mErrorHandler = null;
        super.onDestroy(owner);
    }
}

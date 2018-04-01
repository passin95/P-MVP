package me.passin.pmvp.mvp;

import android.arch.lifecycle.LifecycleOwner;

import com.passin.pmvp.base.BasePresenter;
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
public class LeftMenuPresenter extends BasePresenter<LeftMenuView>{

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public LeftMenuPresenter(LeftMenuView rootView) {
        super(rootView);
    }


    @Override
    public void onDestroy(LifecycleOwner owner) {
        super.onDestroy(owner);
    }
}

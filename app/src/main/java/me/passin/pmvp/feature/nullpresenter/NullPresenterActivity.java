package me.passin.pmvp.feature.nullpresenter;

import android.os.Bundle;
import com.passin.pmvp.base.BaseActivity;
import com.passin.pmvp.integration.AppManager;
import com.passin.pmvp.rx.rxerrorhandler.RxErrorHandler;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import me.passin.pmvp.R;
import me.passin.pmvp.data.model.UserModel;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/5/15 21:33
 * </pre>
 */
public class NullPresenterActivity extends BaseActivity{


    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    UserModel mUserModel;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_null_presenter;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mUserModel.test();
        addDispose(Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mAppManager.showSnackbar(NullPresenterActivity.this, "我(AppManager)成功注入啦");
                    }
                }));
    }
}

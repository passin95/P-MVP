package ${presenterPackageName};

import android.arch.lifecycle.LifecycleOwner;

import com.passin.pmvp.di.scope.PageScope;
import com.passin.pmvp.base.BasePresenter;
import com.passin.pmvp.rx.rxerrorhandler.RxErrorHandler;

import javax.inject.Inject;



/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: ${.now?string("yyyy/MM/dd")}
 * </pre>
 */
@PageScope
public class ${pageName}Presenter extends BasePresenter<${pageName}View> {

    @Inject
    RxErrorHandler mErrorHandler;

  



    @Inject
    public ${pageName}Presenter (${pageName}View rootView) {
        super(rootView);
    }
	

    @Override
    public void onDestroy(LifecycleOwner owner) {
        mErrorHandler = null;
        super.onDestroy(owner);
    }
	
}

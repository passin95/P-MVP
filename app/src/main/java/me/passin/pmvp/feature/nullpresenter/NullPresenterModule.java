package me.passin.pmvp.feature.nullpresenter;

import android.arch.lifecycle.LifecycleOwner;
import com.passin.pmvp.di.scope.PageScope;
import dagger.Module;
import dagger.Provides;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/5/15 21:52
 * </pre>
 */
@Module
public class NullPresenterModule {

    @PageScope
    @Provides
    LifecycleOwner provideLifecycleOwner(NullPresenterActivity activity) {
        return activity;
    }

}

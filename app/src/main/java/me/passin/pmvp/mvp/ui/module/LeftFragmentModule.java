package me.passin.pmvp.mvp.ui.module;

import android.arch.lifecycle.LifecycleOwner;

import com.passin.pmvp.di.scope.PageScope;

import dagger.Module;
import dagger.Provides;
import me.passin.pmvp.mvp.ui.fragment.LeftMenuFragment;
import me.passin.pmvp.mvp.IView.LeftMenuView;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:42
 * </pre>
 */
@Module
public class LeftFragmentModule {

    @PageScope
    @Provides
    LeftMenuView provideMainView(LeftMenuFragment fragment) {
        return fragment;
    }

    @PageScope
    @Provides
    LifecycleOwner provideLifecycleOwner(LeftMenuFragment fragment) {
        return fragment;
    }
}

package me.passin.pmvp.di.module;

import android.arch.lifecycle.LifecycleOwner;

import com.passin.pmvp.di.scope.PageScope;

import dagger.Module;
import dagger.Provides;
import me.passin.pmvp.mvp.MainActivity;
import me.passin.pmvp.mvp.MainView;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:42
 * </pre>
 */
@Module
public class MainActivityModule {

    @PageScope
    @Provides
    MainView provideView(MainActivity activity) {
        return activity;
    }

    @PageScope
    @Provides
    LifecycleOwner provideLifecycleOwner(MainActivity activity) {
        return activity;
    }

}

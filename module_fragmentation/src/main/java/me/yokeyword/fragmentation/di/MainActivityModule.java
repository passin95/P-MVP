package me.yokeyword.fragmentation.di;

import android.arch.lifecycle.LifecycleOwner;

import com.passin.pmvp.base.IView;
import com.passin.pmvp.di.scope.PageScope;

import dagger.Module;
import dagger.Provides;
import me.yokeyword.fragmentation.mvp.demo_wechat.MainActivity;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/30 17:17
 * </pre>
 */
@Module
public class MainActivityModule {

    @PageScope
    @Provides
    IView provideView(MainActivity activity) {
        return activity;
    }


    @PageScope
    @Provides
    LifecycleOwner provideLifecycleOwner(MainActivity activity) {
        return activity;
    }


}

package me.yokeyword.fragmentation.app.di;

import com.passin.pmvp.di.scope.PageScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.yokeyword.fragmentation.mvp.demo_wechat.MainActivity;
import me.yokeyword.fragmentation.mvp.demo_wechat.MainActivityModule;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/30 17:16
 * </pre>
 */
@Module
public abstract class FragmentationActivitiesModule {

    @PageScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivity();
}

package me.passin.pmvp.di;

import com.passin.pmvp.di.scope.PageScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.passin.pmvp.mvp.ui.activity.MainActivity;
import me.passin.pmvp.di.module.MainActivityModule;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:41
 * </pre>
 */
@Module
public abstract class MainActivitiesModule {

    @PageScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivity();

}

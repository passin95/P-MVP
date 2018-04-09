package me.passin.pmvp.app.di.module;

import com.passin.pmvp.di.scope.PageScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.passin.pmvp.mvp.ui.fragment.LeftMenuFragment;
import me.passin.pmvp.mvp.ui.module.LeftFragmentModule;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/24 0:08
 * </pre>
 */
@Module
public abstract class MainFragmentsModule {


    @PageScope
    @ContributesAndroidInjector(modules = LeftFragmentModule.class)
    abstract LeftMenuFragment contributeLeftMenuFragment();
}

package me.passin.pmvp.di;

import com.passin.pmvp.di.scope.PageScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.passin.pmvp.di.module.LeftFragmentModule;
import me.passin.pmvp.mvp.LeftMenuFragment;

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

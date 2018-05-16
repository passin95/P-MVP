package me.passin.pmvp.di;

import com.passin.pmvp.di.scope.PageScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.passin.pmvp.feature.multiplexpresenter.MultiplexPresenterActivity;
import me.passin.pmvp.feature.multiplexpresenter.MultiplexPresenterModule;
import me.passin.pmvp.feature.nullpresenter.NullPresenterActivity;
import me.passin.pmvp.feature.nullpresenter.NullPresenterModule;
import me.passin.pmvp.feature.user.UserActivity;
import me.passin.pmvp.feature.user.UserModule;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:41
 * </pre>
 */
@Module
public interface  MainActivitiesModule {

    @PageScope
    @ContributesAndroidInjector(modules = {UserModule.class})
    UserActivity contributeUserActivity();


    @PageScope
    @ContributesAndroidInjector(modules = {MultiplexPresenterModule.class})
    MultiplexPresenterActivity contributeMultiplexPresenterActivity();


    @PageScope
    @ContributesAndroidInjector(modules = {NullPresenterModule.class})
    NullPresenterActivity contributeNullPresenterActivity();

}

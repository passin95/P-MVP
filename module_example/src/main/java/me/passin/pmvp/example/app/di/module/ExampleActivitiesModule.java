package me.passin.pmvp.example.app.di.module;

import com.passin.pmvp.di.component.BaseActivityComponent;
import com.passin.pmvp.di.scope.PageScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.passin.pmvp.example.mvp.ui.module.UserModule;
import me.passin.pmvp.example.mvp.ui.activity.UserActivity;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:41
 * </pre>
 */
@Module(subcomponents = {BaseActivityComponent.class})
public abstract class ExampleActivitiesModule {


    @PageScope
    @ContributesAndroidInjector(modules = UserModule.class)
    abstract UserActivity contributeMainActivity();

}

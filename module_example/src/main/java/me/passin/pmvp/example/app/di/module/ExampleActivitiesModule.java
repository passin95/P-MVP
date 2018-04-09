package me.passin.pmvp.example.app.di.module;

import com.passin.pmvp.di.scope.PageScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.passin.pmvp.example.mvp.ui.activity.UserActivity;
import me.passin.pmvp.example.mvp.ui.module.UserModule;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:41
 * </pre>
 */
@Module
public abstract class ExampleActivitiesModule {


    @PageScope
    @ContributesAndroidInjector(modules = UserModule.class)
    abstract UserActivity contributeMainActivity();

}

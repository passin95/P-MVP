package me.yokeyword.fragmentation.app.di.component;

import com.passin.pmvp.di.component.ArmsComponent;
import com.passin.pmvp.di.scope.AppScope;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import me.yokeyword.fragmentation.app.ModuleApplication;
import me.yokeyword.fragmentation.app.di.FragmentationActivitiesModule;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/27 13:21
 * </pre>
 */
@AppScope
@Component(dependencies = ArmsComponent.class,modules = {AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class, FragmentationActivitiesModule.class})
public interface ModuleComponent {
    void inject(ModuleApplication lifecyclesImpl1);
}

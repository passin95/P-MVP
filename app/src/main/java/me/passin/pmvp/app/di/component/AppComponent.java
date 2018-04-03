package me.passin.pmvp.app.di.component;

import com.passin.pmvp.di.component.ArmsComponent;
import com.passin.pmvp.di.scope.AppScope;

import dagger.Component;
import me.passin.pmvp.app.MainApplication;
import me.passin.pmvp.app.di.module.MainActivitiesModule;
import me.passin.pmvp.app.di.module.MainFragmentsModule;
import me.passin.pmvp.example.app.di.module.ExampleActivitiesModule;
import me.passin.pmvp.example.app.di.module.ExampleFragmentsModule;
import me.yokeyword.fragmentation.app.di.FragmentationActivitiesModule;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/22 9:37
 * </pre>
 */
@AppScope
@Component(dependencies = ArmsComponent.class,
        modules = {
              //其它模块的所有注入Module
                MainActivitiesModule.class,
                MainFragmentsModule.class,
                ExampleActivitiesModule.class,
                ExampleFragmentsModule.class,
                FragmentationActivitiesModule.class
        })
public interface AppComponent {
    void inject(MainApplication mainApplication);
}

package me.passin.pmvp.di.component;

import com.passin.pmvp.di.component.ArmsComponent;
import com.passin.pmvp.di.scope.AppScope;

import dagger.Component;
import me.passin.pmvp.app.MainApplication;
import me.passin.pmvp.di.MainActivitiesModule;
import me.passin.pmvp.di.MainFragmentsModule;
import me.passin.pmvp.example.di.ExampleActivitiesModule;
import me.passin.pmvp.example.di.ExampleFragmentsModule;
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

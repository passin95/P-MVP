package me.passin.pmvp.example.app.di.component;

import com.passin.pmvp.di.component.ArmsComponent;
import com.passin.pmvp.di.scope.AppScope;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import me.passin.pmvp.example.app.ModuleApplication;
import me.passin.pmvp.example.app.di.ExampleActivitiesModule;
import me.passin.pmvp.example.app.di.ExampleFragmentsModule;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/27 13:21
 * </pre>
 */
@AppScope
@Component(dependencies = ArmsComponent.class,modules = {
        ExampleActivitiesModule.class,
        //没有fragment注入时改为AndroidSupportInjectionModule，否则使用ExampleFragmentsModule
        AndroidSupportInjectionModule.class})
public interface ModuleComponent {
    void inject(ModuleApplication moduleApplication);
}

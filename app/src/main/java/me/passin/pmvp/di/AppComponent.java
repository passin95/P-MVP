package me.passin.pmvp.di;

import com.passin.pmvp.di.component.ArmsComponent;
import com.passin.pmvp.di.scope.AppScope;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import me.passin.pmvp.app.MainApplication;

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
                //由于MainFragmentsModule没有fragment注入，所以暂时要加
                AndroidSupportInjectionModule.class
        })
public interface AppComponent {
    void inject(MainApplication mainApplication);
}

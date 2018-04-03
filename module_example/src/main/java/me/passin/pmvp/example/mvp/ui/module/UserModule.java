package me.passin.pmvp.example.mvp.ui.module;

import android.arch.lifecycle.LifecycleOwner;

import com.passin.pmvp.di.scope.PageScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import me.passin.pmvp.example.mvp.ui.activity.UserActivity;
import me.passin.pmvp.example.mvp.IView.UserView;
import me.passin.pmvp.example.mvp.model.entity.User;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:42
 * </pre>
 */
@Module
public class UserModule {

    @PageScope
    @Provides
    UserView provideView(UserActivity activity) {
        return activity;
    }

    @PageScope
    @Provides
    LifecycleOwner provideLifecycleOwner(UserActivity activity) {
        return activity;
    }

    @PageScope
    @Provides
    List<User> provideUserList() {
        return new ArrayList<>();
    }

}

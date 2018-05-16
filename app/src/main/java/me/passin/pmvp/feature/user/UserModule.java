package me.passin.pmvp.feature.user;

import android.arch.lifecycle.LifecycleOwner;
import com.passin.pmvp.di.scope.PageScope;
import dagger.Module;
import dagger.Provides;
import java.util.ArrayList;
import java.util.List;
import me.passin.pmvp.data.bean.User;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/5/15 17:10
 * </pre>
 */
@Module
public class UserModule {

    @PageScope
    @Provides
    UserView provideUserView(UserActivity activity) {
        return activity;
    }

    @PageScope
    @Provides
    LifecycleOwner provideLifecycleOwner(UserActivity activity) {
        return activity;
    }

    @PageScope
    @Provides
    UserAdapter provideUserAdapter(List<User> userList) {
        return new UserAdapter(userList);
    }

    @PageScope
    @Provides
    List<User> provideUserList() {
        return new ArrayList();
    }

}

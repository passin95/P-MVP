package me.passin.pmvp.data.model;

import android.arch.lifecycle.LifecycleOwner;
import com.passin.pmvp.base.BaseModel;
import com.passin.pmvp.di.scope.PageScope;
import com.passin.pmvp.http.repository.IRepositoryManager;
import com.passin.pmvp.util.PmvpUtils;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import me.passin.pmvp.data.api.service.UserService;
import me.passin.pmvp.data.bean.User;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/21 22:08
 * </pre>
 */

@PageScope
public class UserModel extends BaseModel{
    public static final int USERS_PER_PAGE = 10;

    @Inject
    public UserModel(IRepositoryManager repositoryManager, LifecycleOwner owner) {
        super(repositoryManager, owner);
    }



    public Observable<List<User>> getUsers(int lastIdQueried,boolean isEvictCache) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getUsers(isEvictCache?"no-cache":"public ,max-age=100",lastIdQueried, USERS_PER_PAGE);
    }

    public void test() {
        PmvpUtils.snackbarText("随意注入Model啦");
    }


}

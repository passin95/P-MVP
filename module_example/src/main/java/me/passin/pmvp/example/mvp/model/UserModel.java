package me.passin.pmvp.example.mvp.model;

import android.arch.lifecycle.LifecycleOwner;

import com.passin.pmvp.base.BaseModel;
import com.passin.pmvp.di.scope.PageScope;
import com.passin.pmvp.http.repository.IRepositoryManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import me.passin.pmvp.example.mvp.model.cache.UserCache;
import me.passin.pmvp.example.mvp.model.entity.User;
import me.passin.pmvp.example.mvp.model.service.UserService;

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



    public Observable<List<User>> getUsers(int lastIdQueried, boolean update) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getUsers(lastIdQueried, USERS_PER_PAGE))
                .flatMap(new Function<Observable<List<User>>, ObservableSource<List<User>>>() {
                    @Override
                    public ObservableSource<List<User>> apply(@NonNull Observable<List<User>> listObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(UserCache.class)
                                .getUsers(listObservable
                                        , new DynamicKey(lastIdQueried)
                                        , new EvictDynamicKey(update))
                                .map(listReply -> listReply.getData());
                    }
                });

    }


}

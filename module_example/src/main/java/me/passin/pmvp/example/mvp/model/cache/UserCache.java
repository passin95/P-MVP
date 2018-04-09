package me.passin.pmvp.example.mvp.model.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;
import me.passin.pmvp.example.mvp.model.entity.User;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/31 23:30
 * </pre>
 */

public interface UserCache {
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<User>>> getUsers(Observable<List<User>> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);
}

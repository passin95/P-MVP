package me.passin.pmvp.data.api.service;

import io.reactivex.Observable;
import java.util.List;
import me.passin.pmvp.data.bean.User;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/31 23:27
 * </pre>
 */

public interface UserService {
    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
//            ,"Cache-Control:no-cache"})
//            ,"Cache-Control:public ,max-age=60"})
    @GET("/users")
    Observable<List<User>> getUsers(@Header("Cache-Control") String cacheHeaders,@Query("since") int lastIdQueried,
            @Query("per_page") int perPage);
}

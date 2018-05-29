package com.passin.pmvp.http.cache;

import android.annotation.SuppressLint;
import com.passin.pmvp.util.NetworkUtils;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/5/29 17:02
 * </pre>
 */
@Singleton
public class CacheInterceptor implements Interceptor {


    @Inject
    public CacheInterceptor() {
    }


    @SuppressLint("MissingPermission")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (NetworkUtils.isConnected()) {
            return response.newBuilder()
                    .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", request.cacheControl().toString())
                    .build();
        }
        return response;
    }
}

package me.passin.pmvp.app.callback;

import android.content.Context;
import com.passin.pmvp.http.GlobalHttpHandler;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:19
 * </pre>
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                    /* 这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                       重新请求token,并重新执行请求 */
//
//        if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {
//            try {
//                List<User> list = ArmsUtils.obtainAppComponentFromContext(context).gson().fromJson(httpResult, new TypeToken<List<User>>() {}.getType());
//                User user = list.get(0);
//                Timber.w("Result ------> " + user.getLogin() + "    ||   Avatar_url------> " + user.getAvatarUrl());
//            } catch (Exception e) {
//                e.printStackTrace();
//                return response;
//            }
//        }

                 /* 这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
                    注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
                    create a new request and modify it accordingly using the new token
                    Request newRequest = chain.request().newBuilder().header("token", newToken)
                                         .build();

                    retry the request

                    response.body().close();
                    如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可
                    如果不需要返回新的结果,则直接把response参数返回出去 */

        return response;
    }

    // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header以及参数加密等操作
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                    /* 如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的request如增加header,不做操作则直接返回request参数
                       return chain.request().newBuilder().header("token", tokenId)
                              .build(); */
        return request;
    }
}

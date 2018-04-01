package com.passin.pmvp.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/16 11:36
 * ========================================
 * http请求回调，比客户端提前一步拿到服务器返回的结果
 * </pre>
 */
public interface GlobalHttpHandler {

    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

//    GlobalHttpHandler EMPTY = new GlobalHttpHandler() {
//        @Override
//        public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
//            //不管是否处理,都必须将response返回出去
//            return response;
//        }
//
//        @Override
//        public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
//            //不管是否处理,都必须将request返回出去
//            return request;
//        }
//    };
}

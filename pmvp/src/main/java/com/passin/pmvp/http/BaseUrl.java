package com.passin.pmvp.http;

import okhttp3.HttpUrl;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/19 12:30
 * ==========================================
 *  * 针对于 BaseUrl 在 App 启动时不能确定,需要请求服务器接口动态获取的应用场景
 * </pre>
 */
public interface BaseUrl {
    /**
     * 在调用 Retrofit API 接口之前,使用 OkHttp 或其他方式,请求到正确的 BaseUrl 并通过此方法返回。
     *
     * @return
     */
    HttpUrl url();
}

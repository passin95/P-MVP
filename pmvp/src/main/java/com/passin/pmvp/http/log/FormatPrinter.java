package com.passin.pmvp.http.log;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/19 11:30
 * </pre>
 */
public interface FormatPrinter {
    /**
     * 打印网络请求信息, 当网络请求时 {{@link okhttp3.RequestBody}} 可以解析的情况。
     *
     * @param request
     * @param bodyString 发送给服务器的请求体中的数据(已解析)
     */
    void printJsonRequest(Request request, String bodyString);

    /**
     * 打印网络请求信息, 当网络请求时 {{@link okhttp3.RequestBody}} 为 {@code null} 或不可解析的情况。
     *
     * @param request
     */
    void printFileRequest(Request request);

    /**
     * 打印网络响应信息, 当网络响应时 {{@link okhttp3.ResponseBody}} 可以解析的情况。
     *
     * @param chainMs 服务器响应耗时(单位毫秒)
     * @param isSuccessful 请求是否成功
     * @param code 响应码
     * @param headers 请求头
     * @param contentType 服务器返回数据的数据类型
     * @param bodyString 服务器返回的数据(已解析)
     * @param segments 域名后面的资源地址
     * @param message 响应信息
     * @param responseUrl 请求地址
     */
    void printJsonResponse(long chainMs, boolean isSuccessful, int code, String headers, MediaType contentType,
                           String bodyString, List<String> segments, String message, String responseUrl);

    /**
     * 打印网络响应信息, 当网络响应时 {{@link okhttp3.ResponseBody}} 为 {@code null} 或不可解析的情况。
     *
     * @param chainMs 服务器响应耗时(单位毫秒)
     * @param isSuccessful 请求是否成功
     * @param code 响应码
     * @param headers 请求头
     * @param segments 域名后面的资源地址
     * @param message 响应信息
     * @param responseUrl 请求地址
     */
    void printFileResponse(long chainMs, boolean isSuccessful, int code, String headers,
                           List<String> segments, String message, String responseUrl);
}

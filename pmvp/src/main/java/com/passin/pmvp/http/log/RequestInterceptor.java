package com.passin.pmvp.http.log;

import android.support.annotation.Nullable;
import com.passin.pmvp.http.GlobalHttpHandler;
import com.passin.pmvp.util.CharacterHandler;
import com.passin.pmvp.util.UrlEncoderUtils;
import com.passin.pmvp.util.ZipUtils;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/19 11:31
 * </pre>
 */
@Singleton
public class RequestInterceptor implements Interceptor {
    @Inject
    @Nullable
    GlobalHttpHandler mHandler;
    @Inject
    FormatPrinter mPrinter;
    @Inject
    Level printLevel;

    public enum Level {
        // 不打印 log
        NONE,
        // 只打印请求信息
        REQUEST,
        // 只打印响应信息
        RESPONSE,
        // 所有数据全部打印
        ALL
    }

    @Inject
    public RequestInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        boolean logRequest = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.REQUEST);

        if (logRequest) {
            // 打印请求信息
            if (request.body() != null && isParseable(request.body().contentType())) {
                mPrinter.printJsonRequest(request, parseParams(request));
            } else {
                mPrinter.printFileRequest(request);
            }
        }

        boolean logResponse = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.RESPONSE);

        long t1 = logResponse ? System.nanoTime() : 0;
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            Timber.w("Http Error: " + e);
            throw e;
        }
        long t2 = logResponse ? System.nanoTime() : 0;

        ResponseBody responseBody = originalResponse.body();

        // 打印响应结果。
        String bodyString = null;
        if (responseBody != null && isParseable(responseBody.contentType())) {
            bodyString = ResponseResult(originalResponse);
        }

        if (logResponse) {
            final List<String> segmentList = request.url().encodedPathSegments();
            final String header = originalResponse.headers().toString();
            final int code = originalResponse.code();
            final boolean isSuccessful = originalResponse.isSuccessful();
            final String message = originalResponse.message();
            final String url = originalResponse.request().url().toString();

            if (responseBody != null && isParseable(responseBody.contentType())) {
                mPrinter.printJsonResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1), isSuccessful,
                        code, header, responseBody.contentType(), bodyString, segmentList, message, url);
            } else {
                mPrinter.printFileResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                        isSuccessful, code, header, segmentList, message, url);
            }

        }

        // 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如 token 超时,重新获取。
        if (mHandler != null)
            return mHandler.onHttpResultResponse(bodyString, chain, originalResponse);

        return originalResponse;
    }

    /**
     * 打印响应结果
     *
     * @param response
     * @return
     */
    @Nullable
    private String ResponseResult(Response response) {
        try {
            // 读取服务器返回的结果。
            ResponseBody responseBody = response.newBuilder().build().body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            // 获取 content 的压缩类型。
            String encoding = response
                    .headers()
                    .get("Content-Encoding");

            Buffer clone = buffer.clone();

            // 解析 response content。
            return parseContent(responseBody, encoding, clone);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }


    /**
     * 解析服务器响应的内容
     *
     * @param responseBody
     * @param encoding
     * @param clone
     * @return
     */
    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            // content 使用 gzip 压缩。
            return ZipUtils.decompressForGzip(clone.readByteArray(), convertCharset(charset));
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {
            // content 使用 zlib 压缩
            return ZipUtils.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));
        } else {
            // content 没有被压缩或使用了其他压缩方式。
            return clone.readString(charset);
        }
    }

    /**
     * 解析请求服务器的请求参数。
     *
     * @param request
     * @return
     */
    public static String parseParams(Request request)  {
        try {
            RequestBody body = request.newBuilder().build().body();
            if (body == null) return "";
            Buffer requestbuffer = new Buffer();
            body.writeTo(requestbuffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String url = requestbuffer.readString(charset);
            if (UrlEncoderUtils.hasUrlEncoded(url)) {
                url = URLDecoder.decode(url, convertCharset(charset));
            }
            return CharacterHandler.jsonFormat(url);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * 是否可以解析。
     *
     * @param mediaType
     * @return
     */
    public static boolean isParseable(MediaType mediaType) {
        return isText(mediaType) || isPlain(mediaType)
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    public static boolean isText(MediaType mediaType) {
        if (mediaType == null || mediaType.type() == null) return false;
        return mediaType.type().equals("text");
    }

    public static boolean isPlain(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) return false;
        return mediaType.subtype().toLowerCase().contains("plain");
    }

    public static boolean isJson(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) return false;
        return mediaType.subtype().toLowerCase().contains("json");
    }

    public static boolean isXml(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) return false;
        return mediaType.subtype().toLowerCase().contains("xml");
    }

    public static boolean isHtml(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) return false;
        return mediaType.subtype().toLowerCase().contains("html");
    }

    public static boolean isForm(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) return false;
        return mediaType.subtype().toLowerCase().contains("x-www-form-urlencoded");
    }

    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1) {
            return s;
        }
        return s.substring(i + 1, s.length() - 1);
    }


}

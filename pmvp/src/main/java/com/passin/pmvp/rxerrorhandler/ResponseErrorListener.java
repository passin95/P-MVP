package com.passin.pmvp.rxerrorhandler;

import android.content.Context;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/13 14:10
 * </pre>
 */
public interface ResponseErrorListener {

    /**
     * 网络请求出现Throwable处理回调
     */
    void handleResponseError(Context context, Throwable t);

    ResponseErrorListener EMPTY = new ResponseErrorListener() {
        @Override
        public void handleResponseError(Context context, Throwable t) {}
    };
}

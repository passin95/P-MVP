package com.passin.pmvp.rxerrorhandler;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/13 15:45
 * </pre>
 */
public class RetryWithDelay implements Function<Observable<Throwable>, ObservableSource<?>> {

    private final int maxRetries;
    private final int retryDelaySecond;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    @Override
    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        //当异常为网络连接超时并未超过重试次数才进行重试
                        if (throwable instanceof SocketTimeoutException && ++retryCount <= maxRetries) {
                            return Observable.timer(retryDelaySecond,
                                    TimeUnit.SECONDS);
                        }
                        return Observable.error(throwable);
                    }
                });
    }
}

package com.passin.pmvp.rx.rxerrorhandler;

import org.reactivestreams.Publisher;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/13 15:56
 * </pre>
 */
public class RetryWithDelayOfFlowable implements Function<Flowable<Throwable>, Publisher<?>> {

    public final String TAG = this.getClass().getSimpleName();
    private final int maxRetries;
    private final int retryDelaySecond;
    private int retryCount;

    public RetryWithDelayOfFlowable(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    @Override
    public Publisher<?> apply(@NonNull Flowable<Throwable> throwableFlowable) throws Exception {
        return throwableFlowable
                .flatMap(new Function<Throwable, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(@NonNull Throwable throwable) throws Exception {
                        // 当异常为网络连接超时并未超过重试次数才进行重试。
                        if (throwable instanceof SocketTimeoutException && ++retryCount <= maxRetries) {
                            return Flowable.timer(retryDelaySecond,
                                    TimeUnit.SECONDS);
                        }

                        return Flowable.error(throwable);
                    }
                });
    }
}
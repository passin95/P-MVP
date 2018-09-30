package com.passin.pmvp.rx.rxerrorhandler;

import io.reactivex.annotations.NonNull;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/13 14:29
 * </pre>
 */
public abstract class ErrorHandleSubscriberOfFlowable<T> extends DisposableSubscriber<T> {
    private RxErrorHandler mErrorHandler;

    public ErrorHandleSubscriberOfFlowable(RxErrorHandler rxErrorHandler) {
        this.mErrorHandler = rxErrorHandler;
    }


    @Override
    public void onComplete() {}

    @Override
    public void onError(@NonNull Throwable t) {
        t.printStackTrace();
        // 如果你某个地方不想使用全局错误处理,则重写 onError(Throwable) 并将 super.onError(e); 删掉。
        // 如果你不仅想使用全局错误处理,还想加入自己的逻辑,则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑。
        mErrorHandler.handleError(t);
    }
}

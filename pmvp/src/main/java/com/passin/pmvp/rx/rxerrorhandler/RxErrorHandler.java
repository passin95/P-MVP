package com.passin.pmvp.rx.rxerrorhandler;

import android.content.Context;

import com.passin.pmvp.util.Preconditions;


/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/13 14:05
 * </pre>
 */
public class RxErrorHandler {
    final ResponseErrorListener responseErrorListener;
    final Context context;

    private RxErrorHandler(Builder builder) {
        responseErrorListener = builder.responseErrorListener;
        context = builder.context;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void handleError(Throwable t) {
        responseErrorListener.handleResponseError(context,t);
    }


    public static final class Builder {
        private ResponseErrorListener responseErrorListener;
        private Context context;

        public Builder() {
        }

        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        public Builder responseErrorListener(ResponseErrorListener responseErrorListener) {
            this.responseErrorListener = responseErrorListener;
            return this;
        }


        public RxErrorHandler build() {
            Preconditions.checkArgument(context == null ? false : true, "Context is required");

            Preconditions.checkArgument(responseErrorListener == null ? false : true, "ResponseErrorListener is required");

            return new RxErrorHandler(this);
        }
    }
}

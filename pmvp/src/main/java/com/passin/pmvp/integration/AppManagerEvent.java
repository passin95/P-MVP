package com.passin.pmvp.integration;

import android.content.Context;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/5/5 15:51
 * </pre>
 */
public class AppManagerEvent {

    public Context context;
    public Object obj;
    public int what;

    public AppManagerEvent( ) {
    }

    public AppManagerEvent(Context context, Object obj) {
        this.context = context;
        this.obj = obj;
    }

    public AppManagerEvent(Context context, int what, Object obj) {
        this.context = context;
        this.obj = obj;
        this.what = what;
    }

    public AppManagerEvent(Object obj, int what) {
        this.obj = obj;
        this.what = what;
    }
}

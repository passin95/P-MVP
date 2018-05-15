package com.passin.pmvp.base;

import android.support.annotation.NonNull;
import com.passin.pmvp.util.PmvpUtils;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/13 16:19
 * </pre>
 */
public interface IView {

    /**
     * 显示加载
     */
    default void showLoading(){

    }

    /**
     * 隐藏加载
     */
    default void hideLoading(){

    }

    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    default void showMessage(@NonNull String message){
        PmvpUtils.snackbarText(message);
    }

    /**
     * 杀死自己
     */
    default void killMyself(){

    }
}

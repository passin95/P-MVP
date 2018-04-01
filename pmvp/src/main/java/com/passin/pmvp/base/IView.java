package com.passin.pmvp.base;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

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
    void showLoading(@NonNull String content);

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 跳转 {@link Activity}
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    void launchActivity(@NonNull Intent intent);

    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    void showMessage(@NonNull String message);

    /**
     * 杀死自己
     */
    void killMyself();
}

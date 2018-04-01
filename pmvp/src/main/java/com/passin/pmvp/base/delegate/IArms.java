package com.passin.pmvp.base.delegate;

import android.support.annotation.NonNull;

import com.passin.pmvp.di.component.ArmsComponent;


/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/17 10:18
 * =========================================
 * 要求框架中的每个 {@link android.app.Application} 都需要实现此类,以满足规范
 * </pre>
 */

public interface IArms {
    @NonNull
    ArmsComponent getArmsComponent();
}

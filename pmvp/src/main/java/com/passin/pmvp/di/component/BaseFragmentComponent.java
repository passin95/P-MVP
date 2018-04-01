package com.passin.pmvp.di.component;

import com.passin.pmvp.base.BaseFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:45
 * </pre>
 */
@Subcomponent(modules = {
        AndroidSupportInjectionModule.class,
})
public interface BaseFragmentComponent extends AndroidInjector<BaseFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseFragment> {
    }
}

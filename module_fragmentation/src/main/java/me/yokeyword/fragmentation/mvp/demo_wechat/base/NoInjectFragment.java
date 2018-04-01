package me.yokeyword.fragmentation.mvp.demo_wechat.base;

import com.passin.pmvp.base.BaseFragment;


/**
 * 懒加载
 * Created by YoKeyword on 16/6/5.
 */
public abstract class NoInjectFragment extends BaseFragment {

    @Override
    public boolean useInject() {
        return false;
    }
}

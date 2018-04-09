package me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.second;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.R2;
import me.yokeyword.fragmentation.mvp.demo_wechat.adapter.WechatPagerFragmentAdapter;
import me.yokeyword.fragmentation.mvp.demo_wechat.base.NoInjectFragment;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class WechatSecondTabFragment extends NoInjectFragment {
    @BindView(R2.id.tab)
    TabLayout mTab;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.viewPager)
    ViewPager mViewPager;

    public static WechatSecondTabFragment newInstance() {

        WechatSecondTabFragment fragment = new WechatSecondTabFragment();
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_wechat_fragment_tab_second, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mToolbar.setTitle(R.string.fragmentation_discover);

        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        mViewPager.setAdapter(new WechatPagerFragmentAdapter(getChildFragmentManager()
                , getString(R.string.fragmentation_all), getString(R.string.fragmentation_more)));
        mTab.setupWithViewPager(mViewPager);
    }


}

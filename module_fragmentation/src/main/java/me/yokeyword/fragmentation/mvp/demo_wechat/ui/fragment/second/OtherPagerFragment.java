package me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.second;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.mvp.demo_wechat.base.NoInjectFragment;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class OtherPagerFragment extends NoInjectFragment {

    public static OtherPagerFragment newInstance() {

        Bundle args = new Bundle();
        OtherPagerFragment fragment = new OtherPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_wechat_fragment_tab_second_pager_other, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}

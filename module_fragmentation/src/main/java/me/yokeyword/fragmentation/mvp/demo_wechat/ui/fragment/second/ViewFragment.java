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
 * Created by YoKey on 17/8/1.
 */

public class ViewFragment extends NoInjectFragment {

    public static ViewFragment newInstance() {
        Bundle args = new Bundle();

        ViewFragment fragment = new ViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_wechat_fragment_view, container, false);
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


}

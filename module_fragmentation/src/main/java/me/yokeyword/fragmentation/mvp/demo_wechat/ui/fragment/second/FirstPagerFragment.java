package me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.second;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.R2;
import me.yokeyword.fragmentation.mvp.demo_wechat.adapter.PagerAdapter;
import me.yokeyword.fragmentation.mvp.demo_wechat.base.NoInjectFragment;
import me.yokeyword.fragmentation.mvp.demo_wechat.event.TabSelectedEvent;
import me.yokeyword.fragmentation.mvp.demo_wechat.listener.OnItemClickListener;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.MainFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class FirstPagerFragment extends NoInjectFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R2.id.recy)
    RecyclerView mRecy;
    PagerAdapter mAdapter;

    private boolean mInAtTop = true;
    private int mScrollTotal;


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_wechat_fragment_tab_second_pager_first, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new PagerAdapter(_mActivity);
        mRecy.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mInAtTop = true;
                } else {
                    mInAtTop = false;
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder holder) {
                // 通知MainFragment跳转至NewFeatureFragment
                ((MainFragment) getParentFragment().getParentFragment()).startBrotherFragment(NewFeatureFragment.newInstance());
            }
        });

        // Init Datas
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String item = "New features";
            items.add(item);
        }
        mAdapter.setDatas(items);
    }

    public static FirstPagerFragment newInstance() {
        FirstPagerFragment fragment = new FirstPagerFragment();
        return fragment;
    }


    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2500);
    }

    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.SECOND) return;

        if (mInAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }


}

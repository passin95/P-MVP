package me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.first;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.R2;
import me.yokeyword.fragmentation.mvp.demo_wechat.adapter.ChatAdapter;
import me.yokeyword.fragmentation.mvp.demo_wechat.base.NoInjectFragment;
import me.yokeyword.fragmentation.mvp.demo_wechat.entity.Chat;
import me.yokeyword.fragmentation.mvp.demo_wechat.event.TabSelectedEvent;
import me.yokeyword.fragmentation.mvp.demo_wechat.listener.OnItemClickListener;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.MainFragment;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class WechatFirstTabFragment extends NoInjectFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R2.id.recy)
    RecyclerView mRecy;

    private boolean mInAtTop = true;
    private int mScrollTotal;

    private ChatAdapter mAdapter;

    public static WechatFirstTabFragment newInstance() {

        Bundle args = new Bundle();

        WechatFirstTabFragment fragment = new WechatFirstTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_wechat_fragment_tab_first, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mToolbar.setTitle(R.string.fragmentation_home);

    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.setOnRefreshListener(this);

        mRecy.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecy.setHasFixedSize(true);
        final int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics());
        mRecy.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, space);
            }
        });
        mAdapter = new ChatAdapter(_mActivity);
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
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                // 因为启动的MsgFragment是MainFragment的兄弟Fragment,所以需要MainFragment.start()

                // 也可以像使用getParentFragment()的方式,拿到父Fragment来操作 或者使用 EventBusActivityScope
                ((MainFragment) getParentFragment()).startBrotherFragment(MsgFragment.newInstance(mAdapter.getMsg(position)));
            }
        });

        List<Chat> chatList = initDatas();
        mAdapter.setDatas(chatList);
    }

    private List<Chat> initDatas() {
        List<Chat> msgList = new ArrayList<>();

        String[] name = new String[]{"Jake", "Eric", "Kenny", "Helen", "Carr"};
        String[] chats = new String[]{"message1", "message2", "message3", "message4", "message5"};

        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * 5);
            Chat chat = new Chat();
            chat.name = name[index];
            chat.message = chats[index];
            msgList.add(chat);
        }
        return msgList;
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
        if (event.position != MainFragment.FIRST) return;

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

package me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passin.pmvp.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.R2;
import me.yokeyword.fragmentation.mvp.demo_wechat.base.NoInjectFragment;
import me.yokeyword.fragmentation.mvp.demo_wechat.event.TabSelectedEvent;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.first.WechatFirstTabFragment;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.second.WechatSecondTabFragment;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.third.WechatThirdTabFragment;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.view.BottomBar;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.view.BottomBarTab;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class MainFragment extends NoInjectFragment {
    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    private BaseFragment[] mFragments = new BaseFragment[3];

    @BindView(R2.id.bottomBar)
     BottomBar mBottomBar;


    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_wechat_fragment_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_message_white_24dp, getString(R.string.fragmentation_msg)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_account_circle_white_24dp, getString(R.string.fragmentation_discover)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_discover_white_24dp, getString(R.string.fragmentation_more)));


        // 模拟未读消息
        mBottomBar.getItem(FIRST).setUnreadCount(9);

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);

                BottomBarTab tab = mBottomBar.getItem(FIRST);
                if (position == FIRST) {
                    tab.setUnreadCount(0);
                } else {
                    tab.setUnreadCount(tab.getUnreadCount() + 1);
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BaseFragment firstFragment = (BaseFragment) findChildFragment(WechatFirstTabFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = WechatFirstTabFragment.newInstance();
            mFragments[SECOND] = WechatSecondTabFragment.newInstance();
            mFragments[THIRD] = WechatThirdTabFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = (BaseFragment) findChildFragment(WechatSecondTabFragment.class);
            mFragments[THIRD] = (BaseFragment) findChildFragment(WechatThirdTabFragment.class);
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {

        }
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(NoInjectFragment targetFragment) {
        start(targetFragment);
    }


}

package me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.third;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.R2;
import me.yokeyword.fragmentation.mvp.demo_wechat.adapter.HomeAdapter;
import me.yokeyword.fragmentation.mvp.demo_wechat.base.NoInjectFragment;
import me.yokeyword.fragmentation.mvp.demo_wechat.entity.Article;
import me.yokeyword.fragmentation.mvp.demo_wechat.event.StartBrotherEvent;
import me.yokeyword.fragmentation.mvp.demo_wechat.listener.OnItemClickListener;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.MainFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class WechatThirdTabFragment extends NoInjectFragment {
    @BindView(R2.id.recy)
     RecyclerView mRecy;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    private HomeAdapter mAdapter;
    private String[] mTitles;
    private String[] mContents;

    public static WechatThirdTabFragment newInstance() {

        Bundle args = new Bundle();

        WechatThirdTabFragment fragment = new WechatThirdTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_wechat_fragment_tab_third, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTitles = getResources().getStringArray(R.array.fragmentation_array_title);
        mContents = getResources().getStringArray(R.array.fragmentation_array_content);

        mToolbar.setTitle(R.string.fragmentation_more);
    }




    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mAdapter = new HomeAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                ((MainFragment) getParentFragment()).startBrotherFragment(DetailFragment.newInstance(mAdapter.getItem(position).getTitle()));
                // 或者使用EventBus
                EventBus.getDefault().post(new StartBrotherEvent(DetailFragment.newInstance(mAdapter.getItem(position).getTitle())));
            }
        });

        // Init Datas
        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Article article = new Article(mTitles[i], mContents[i]);
            articleList.add(article);
        }
        mAdapter.setDatas(articleList);
    }


}

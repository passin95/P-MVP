package me.passin.pmvp.feature.multiplexpresenter;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.passin.pmvp.base.BaseActivity;
import com.passin.pmvp.util.PmvpUtils;
import com.yanzhenjie.permission.AndPermission;
import javax.inject.Inject;
import me.passin.pmvp.R;
import me.passin.pmvp.app.AppUtils;
import me.passin.pmvp.feature.user.UserAdapter;
import me.passin.pmvp.feature.user.UserPresenter;
import me.passin.pmvp.feature.user.UserView;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/5/15 21:47
 * </pre>
 */
public class MultiplexPresenterActivity extends BaseActivity implements UserView{

    /**
     * 只要实现 P 所对应的 V，便可随意注入 P。
     */
    @Inject
    UserPresenter mPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    @Inject
    UserAdapter mAdapter;


    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_user;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        PmvpUtils.configRecyclerView(mRecyclerView, new GridLayoutManager(this, 2));
        AppUtils.configBaseQuickAdapter(mAdapter,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.requestUsers(true);
        });
        mAdapter.setOnLoadMoreListener(() -> {
            mPresenter.requestUsers(false);
        });

        mPresenter.requestUsers(true);
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .rationale((context, data, executor) ->{
                    PmvpUtils.snackbarText("请给我权限不然没法用啊");
                    executor.execute();
                })
                .onGranted(data -> PmvpUtils.snackbarText("权限申请通过"))
                .onDenied(data -> PmvpUtils.snackbarText("申请权限被拒绝"))
                .start();
    }



    @Override
    public void startRefresh() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void endRefresh() {
        mRefreshLayout.setRefreshing(false);
    }


    @Override
    public void endLoadMore() {
        mAdapter.loadMoreComplete();
    }

    @Override
    public void loadMoreFail() {
        mAdapter.loadMoreFail();
    }
}

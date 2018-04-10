package me.passin.pmvp.example.mvp.ui.activity;

import android.Manifest.permission;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.paginate.Paginate;
import com.passin.pmvp.base.BaseActivity;
import com.passin.pmvp.util.PmvpUtils;
import com.passin.pmvp.util.ToastUtils;
import java.util.List;
import me.passin.pmvp.example.R;
import me.passin.pmvp.example.R2;
import me.passin.pmvp.example.mvp.IView.UserView;
import me.passin.pmvp.example.mvp.presenter.UserPresenter;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class UserActivity extends BaseActivity<UserPresenter> implements UserView,EasyPermissions.PermissionCallbacks {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout mRefreshLayout;


    private Paginate mPaginate;
    private boolean isLoadingMore;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.example_activity_user;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        PmvpUtils.configRecyclerView(mRecyclerView, new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mPresenter.getUserAdapter());
        initPaginate();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestUsers(true);
            }
        });
        mPresenter.requestUsers(true);

        String[] perms = {permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            ToastUtils.showLong("is ok");
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "请求权限",
                    1, perms);
        }
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.requestUsers(false);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void showLoading(@NonNull String content) {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showLong(message);
    }

    @Override
    public void killMyself() {

    }


    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        overridePendingTransition(R.anim.v_fragment_pop_enter, R.anim.v_fragment_pop_exit);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == 1) {
            ToastUtils.showLong("is ok");
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}

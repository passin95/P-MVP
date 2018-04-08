package me.passin.pmvp.example.mvp.presenter;

import android.arch.lifecycle.LifecycleOwner;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.passin.pmvp.base.BasePresenter;
import com.passin.pmvp.di.scope.PageScope;
import com.passin.pmvp.rx.rxerrorhandler.ErrorHandleSubscriber;
import com.passin.pmvp.rx.rxerrorhandler.RetryWithDelay;
import com.passin.pmvp.rx.rxerrorhandler.RxErrorHandler;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.passin.pmvp.example.mvp.IView.UserView;
import me.passin.pmvp.example.mvp.model.UserModel;
import me.passin.pmvp.example.mvp.model.entity.User;
import me.passin.pmvp.example.mvp.ui.adapter.UserAdapter;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:43
 * </pre>
 */

@PageScope
public class UserPresenter extends BasePresenter<UserView>{


    //可直接注入任意Model，如果该Model不是必使用，可用Lazy，使用时再初始化进行优化
    @Inject
    Lazy<UserModel> mModel;
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    List<User> mUsers;

    private UserAdapter mAdapter;

    private int lastUserId = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public UserPresenter(UserView rootView) {
        super(rootView);
    }

    public BaseQuickAdapter getUserAdapter() {
        if (mAdapter == null) {
            mAdapter = new UserAdapter(mUsers);
        }
        return mAdapter;
    }

    public void requestUsers(boolean pullToRefresh) {
        if (pullToRefresh) lastUserId = 1;//下拉刷新默认只请求第一页

        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b

        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pullToRefresh && isFirst) {//默认在第一次下拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }

        mModel.get().getUsers(lastUserId, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading("");//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .subscribe(new ErrorHandleSubscriber<List<User>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDispose(d);
                    }

                    @Override
                    public void onNext(List<User> users) {
                        lastUserId = users.get(users.size() - 1).getId();//记录最后一个id,用于下一次请求
                        if (pullToRefresh) mUsers.clear();//如果是下拉刷新则清空列表
                        preEndIndex = mUsers.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mUsers.addAll(users);
                        if (pullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else
                            mAdapter.notifyItemRangeInserted(preEndIndex, users.size());
                    }
                });
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        mErrorHandler = null;
        super.onDestroy(owner);
    }
}

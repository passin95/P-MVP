package me.passin.pmvp.feature.user;

import android.arch.lifecycle.LifecycleOwner;
import com.passin.pmvp.base.BasePresenter;
import com.passin.pmvp.di.scope.PageScope;
import com.passin.pmvp.rx.rxerrorhandler.BaseErrorHandleSubscriber;
import com.passin.pmvp.rx.rxerrorhandler.RetryWithDelay;
import com.passin.pmvp.rx.rxerrorhandler.RxErrorHandler;
import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
import me.passin.pmvp.data.bean.User;
import me.passin.pmvp.data.model.UserModel;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:43
 * </pre>
 */

@PageScope
public class UserPresenter extends BasePresenter<UserView> {


    //可直接注入任意Model，如果该Model不是必使用，可用Lazy，使用时再初始化进行优化
    @Inject
    Lazy<UserModel> mModel;
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    List<User> mUsers;
    @Inject
    UserAdapter mAdapter;

    private int lastUserId = 1;
    private boolean isFirst = true;

    private boolean isLoadMoreEnd = false;

    @Inject
    public UserPresenter(UserView rootView) {
        super(rootView);
    }


    public void requestUsers(boolean pullToRefresh) {
        if (pullToRefresh) {
            lastUserId = 1;//下拉刷新默认只请求第一页
        }

        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pullToRefresh && isFirst) {//默认在第一次下拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }

        addDispose(mModel.get().getUsers(lastUserId,isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh) {
                        isLoadMoreEnd = false;
                        //这里的作用是防止下拉刷新的时候还可以上拉加载
                        mAdapter.setEnableLoadMore(false);
                        //显示下拉刷新的进度条
                        mRootView.startRefresh();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh) {
                        mAdapter.setEnableLoadMore(true);
                        mRootView.endRefresh();//隐藏下拉刷新的进度条
                    }
                })
                .subscribeWith(new BaseErrorHandleSubscriber<List<User>>(mErrorHandler) {

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (!pullToRefresh) {
                            mRootView.loadMoreFail();
                        }
                    }

                    @Override
                    public void onNext(List<User> users) {
                        lastUserId = users.get(users.size() - 1).getId();//记录最后一个id,用于下一次请求
                        if (pullToRefresh) {
                            mAdapter.setNewData(users);
                        } else {
                            if (users.size() > 0) {
                                mAdapter.addData(users);
                            }
                        }
                        //每页数据低于15时 不再可以加载更多
//                        if (users.size() < 15) {
//                            mAdapter.loadMoreEnd(pullToRefresh);
//                        } else {
//                            mAdapter.loadMoreComplete();
//                        }
                        //这里用变量进行模拟
                        if (!isLoadMoreEnd) {
                            mAdapter.loadMoreComplete();
                            isLoadMoreEnd = true;
                        } else {
                            mAdapter.loadMoreEnd(pullToRefresh);
                        }
                    }
                }));
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        mErrorHandler = null;
        super.onDestroy(owner);
    }
}

package me.passin.pmvp.feature.user;

import com.passin.pmvp.base.IView;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/5/15 17:12
 * </pre>
 */
public interface UserView extends IView{

    void startRefresh();

    void endRefresh();

    void endLoadMore();

    void loadMoreFail();
}

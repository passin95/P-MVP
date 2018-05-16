package me.passin.pmvp.feature;

import android.content.Intent;
import android.os.Bundle;
import butterknife.OnClick;
import com.passin.pmvp.base.BaseActivity;
import me.passin.pmvp.R;
import me.passin.pmvp.feature.multiplexpresenter.MultiplexPresenterActivity;
import me.passin.pmvp.feature.nullpresenter.NullPresenterActivity;
import me.passin.pmvp.feature.user.UserActivity;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/5/15 21:34
 * </pre>
 */
public class HomeActivity extends BaseActivity{

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @OnClick(R.id.tv_normal)
    public void jumpToUserActivity() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_no_p)
    public void jumpToNoPActivity() {
        Intent intent = new Intent(this, NullPresenterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_multiplex_p)
    public void jumpToMultiplexPActivity() {
        Intent intent = new Intent(this, MultiplexPresenterActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean useInject() {
        return false;
    }

}

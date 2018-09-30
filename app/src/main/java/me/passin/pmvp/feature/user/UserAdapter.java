package me.passin.pmvp.feature.user;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.passin.pmvp.http.glide.GlideApp;
import java.util.List;
import me.passin.pmvp.R;
import me.passin.pmvp.data.bean.User;


/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/31 23:55
 * </pre>
 */

public class UserAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    public UserAdapter(@Nullable List<User> data) {
        super(R.layout.item_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        GlideApp.with(mContext).load(item.getAvatarUrl()).centerCrop().into((ImageView) helper.getView(R.id.iv_avatar));
    }

}

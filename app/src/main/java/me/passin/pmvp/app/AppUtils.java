package me.passin.pmvp.app;

import android.support.v7.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import me.passin.pmvp.widget.CustomLoadMoreView;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/5/15 17:29
 * </pre>
 */
public class AppUtils {

    public static void configBaseQuickAdapter(BaseQuickAdapter adapter,RecyclerView recyclerView) {
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.bindToRecyclerView(recyclerView);
    }
}

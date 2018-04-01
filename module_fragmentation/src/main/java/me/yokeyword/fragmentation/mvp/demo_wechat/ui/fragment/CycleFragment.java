package me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.R2;
import me.yokeyword.fragmentation.mvp.demo_wechat.base.NoInjectFragment;


/**
 * Created by YoKeyword on 16/2/7.
 */
public class CycleFragment extends NoInjectFragment {
    private static final String ARG_NUMBER = "arg_number";
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.tv_name)
    TextView mTvName;
    //    Unbinder unbinder;
//
//    private Toolbar mToolbar;
//    private TextView mTvName;
//    private Button mBtnNext, mBtnNextWithFinish;
    private int mNumber;


    public static CycleFragment newInstance(int number) {
        CycleFragment fragment = new CycleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER, number);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mNumber = args.getInt(ARG_NUMBER);
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_fragment_cycle, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String title = "CyclerFragment " + mNumber;

        mToolbar.setTitle(title);

        mTvName.setText(title + "\n" + getString(R.string.fragmentation_can_swipe));
    }

    @OnClick(R2.id.btn_next)
    public void next() {
        start(CycleFragment.newInstance(mNumber + 1));
    }

    @OnClick(R2.id.btn_next_with_finish)
    public void nextWithFinish() {
        startWithPop(CycleFragment.newInstance(mNumber + 1));
    }






}

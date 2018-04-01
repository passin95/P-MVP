package me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.third;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.R2;
import me.yokeyword.fragmentation.mvp.demo_wechat.base.NoInjectFragment;
import me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.CycleFragment;


/**
 * Created by YoKeyword on 16/2/7.
 */
public class ModifyDetailFragment extends NoInjectFragment {
    private static final String ARG_TITLE = "arg_title";

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.et_modify_title)
    EditText mEtModiyTitle;

    private String mTitle;

    public static ModifyDetailFragment newInstance(String title) {
        Bundle args = new Bundle();
        ModifyDetailFragment fragment = new ModifyDetailFragment();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_fragment_modify_detail, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mEtModiyTitle.setText(mTitle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mTitle = args.getString(ARG_TITLE);
        }
    }


    @OnClick(R2.id.btn_modify)
    public void modify() {
        Bundle bundle = new Bundle();
        bundle.putString(DetailFragment.KEY_RESULT_TITLE, mEtModiyTitle.getText().toString());
        setFragmentResult(RESULT_OK, bundle);

        Toast.makeText(_mActivity, R.string.fragmentation_modify_success, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R2.id.btn_next)
    public void next() {
        start(CycleFragment.newInstance(1));
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        hideSoftInput();
    }


}

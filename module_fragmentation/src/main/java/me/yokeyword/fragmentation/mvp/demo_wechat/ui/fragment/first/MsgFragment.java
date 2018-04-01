package me.yokeyword.fragmentation.mvp.demo_wechat.ui.fragment.first;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import me.yokeyword.fragmentation.R;
import me.yokeyword.fragmentation.R2;
import me.yokeyword.fragmentation.mvp.demo_wechat.adapter.MsgAdapter;
import me.yokeyword.fragmentation.mvp.demo_wechat.base.NoInjectFragment;
import me.yokeyword.fragmentation.mvp.demo_wechat.entity.Chat;
import me.yokeyword.fragmentation.mvp.demo_wechat.entity.Msg;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class MsgFragment extends NoInjectFragment {
    private static final String ARG_MSG = "arg_msg";

    @BindView(R2.id.toolbar)
     Toolbar mToolbar;
    @BindView(R2.id.recy)
    RecyclerView mRecy;
    @BindView(R2.id.et_send)
    EditText mEtSend;
    @BindView(R2.id.btn_send)
    Button mBtnSend;

    private Chat mChat;
    private MsgAdapter mAdapter;

    public static MsgFragment newInstance(Chat msg) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_MSG, msg);
        MsgFragment fragment = new MsgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChat = getArguments().getParcelable(ARG_MSG);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentation_wechat_fragment_tab_first_msg, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mToolbar.setTitle(mChat.name);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿

        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mRecy.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecy.setHasFixedSize(true);
        mAdapter = new MsgAdapter(_mActivity);
        mRecy.setAdapter(mAdapter);

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEtSend.getText().toString().trim();
                if (TextUtils.isEmpty(str)) return;

                mAdapter.addMsg(new Msg(str));
                mEtSend.setText("");
                mRecy.scrollToPosition(mAdapter.getItemCount() - 1);
            }
        });

        mAdapter.addMsg(new Msg(mChat.message));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecy = null;
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }


}

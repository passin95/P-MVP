package me.yokeyword.fragmentation.mvp.demo_wechat.event;

import com.passin.pmvp.base.BaseFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class StartBrotherEvent {
    public BaseFragment targetFragment;

    public StartBrotherEvent(BaseFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}

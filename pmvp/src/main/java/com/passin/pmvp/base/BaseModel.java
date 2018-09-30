package com.passin.pmvp.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.passin.pmvp.http.repository.IRepositoryManager;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/14 10:01
 * </pre>
 * ------------------------------------------
 * Model 层用于管理网络请求、数据获取、数据缓存等一切数据来源。
 */
public class BaseModel implements LifecycleObserver {

    /**
     * 用于管理网络请求。
     */
    protected IRepositoryManager mRepositoryManager;


    public BaseModel(IRepositoryManager repositoryManager,LifecycleOwner owner) {
        this.mRepositoryManager = repositoryManager;
        owner.getLifecycle().addObserver(this);
    }

    /**
     * 绑定 Activity onDestroy() 生命周期。
     * @param owner
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner) {
        mRepositoryManager = null;
        owner.getLifecycle().removeObserver(this);
    }
}

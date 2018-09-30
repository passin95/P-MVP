package com.passin.pmvp.integration;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.passin.pmvp.util.SnackbarUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/20 21:51
 * </pre>
 */
@Singleton
public class AppManager {

    private final String TAG = this.getClass().getSimpleName();
    public static final String APPMANAGER_MESSAGE = "appmanager_message";
    /**
     * true 为不需要加入到 Activity 容器进行统一管理,默认为 false
     */
    public static final String IS_NOT_ADD_ACTIVITY_LIST = "is_not_add_activity_list";
    public static final int SHOW_SNACKBAR = 5001;
    public static final int APP_EXIT = 5002;
    @Inject
    public Application mApplication;
    /**
     * 管理所有存活的 Activity, 容器中的顺序仅仅是 Activity 的创建顺序, 并不能保证和 Activity 任务栈顺序一致。
     */
    private List<Activity> mActivityList = new LinkedList<>();

    ReentrantReadWriteLock activityListLock = new ReentrantReadWriteLock();


    @Inject
    public AppManager() {
        EventBus.getDefault().register(this);
    }

    public static void post(AppManagerEvent msg) {
        EventBus.getDefault().post(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(AppManagerEvent message) {
        switch (message.what) {
            case SHOW_SNACKBAR:
                if (message.obj == null) {
                    break;
                }
                showSnackbar(message.context, (String) message.obj);
                break;
            case APP_EXIT:
                appExit();
                break;
            default:
                Timber.tag(TAG).w("The message.what not match");
                break;
        }
    }

    public void showSnackbar(Context context, String message) {
        if (context != null && context instanceof Activity) {
            SnackbarUtils.with(((Activity) context).getWindow().getDecorView()
                    .findViewById(android.R.id.content)).setMessage(message).show();
        } else if (getTopActivity() != null) {
            SnackbarUtils.with(getTopDecorView()).setMessage(message).show();
        } else {
            Timber.tag(TAG).w("mTopActivity == null when showSnackbar(String,boolean)");
        }
    }


    public void startActivity(Intent intent) {
        if (getTopActivity() == null) {
            Timber.tag(TAG).w("mCurrentActivity == null when startActivity(Intent)");
            // 如果没有前台的 activity 就使用 new_task 模式启动 activity。
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mApplication.startActivity(intent);
            return;
        }
        getTopActivity().startActivity(intent);
    }


    public View getTopDecorView() {
        return getTopActivity().getWindow().getDecorView()
                .findViewById(android.R.id.content);
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        killAll();
        try {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取最近启动的一个 {@link Activity}, 此方法不保证获取到的 {@link Activity} 正处于前台可见状态，
     * 即使 App 进入后台或在这个 {@link Activity} 中打开一个之前已经存在的 {@link Activity},
     * 这时调用此方法还是会返回这个最近启动的 {@link Activity}, 因此基本不会出现 {@code null} 的情况。
     * 比较适合大部分的使用场景, 如 startActivity。
     * <p>
     * Tips: mActivityList 容器中的顺序仅仅是 Activity 的创建顺序, 并不能保证和 Activity 任务栈顺序一致。
     */
    public Activity getTopActivity() {
        activityListLock.readLock().lock();
        if (mActivityList.size() > 0) {
            Activity activity = mActivityList.get(mActivityList.size() - 1);
            activityListLock.readLock().unlock();
            return activity;
        } else {
            activityListLock.readLock().unlock();
            return null;
        }
    }

    /**
     * 关闭所有管理列表的 {@link Activity}。
     */
    public void killAll() {
        activityListLock.writeLock().lock();
        Iterator<Activity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            iterator.remove();
            next.finish();
        }
        activityListLock.writeLock().unlock();
    }

    /**
     * 返回一个存储所有未销毁的 {@link Activity} 的集合。
     */
    public List<Activity> getActivityList() {
        return mActivityList;
    }

    /**
     * 添加 {@link Activity} 到集合。
     */
    public void addActivity(Activity activity) {
        activityListLock.writeLock().lock();
        List<Activity> activities = getActivityList();
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
        activityListLock.writeLock().unlock();
    }

    /**
     * 删除集合里的指定的 {@link Activity} 实例。
     *
     * @param {@link Activity}
     */
    public void removeActivity(Activity activity) {
        activityListLock.writeLock().lock();
        if (mActivityList.contains(activity)) {
            mActivityList.remove(activity);
        }
        activityListLock.writeLock().unlock();
    }


    /**
     * 关闭指定的 {@link Activity} class 的所有的实例。
     */
    public void killActivity(Class<?> activityClass) {
        activityListLock.writeLock().lock();
        Iterator<Activity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();

            if (next.getClass().equals(activityClass)) {
                iterator.remove();
                next.finish();
            }
        }
        activityListLock.writeLock().unlock();
    }

    /**
     * 指定的 {@link Activity} 实例是否存活。
     *
     * @param {@link Activity}
     */
    public boolean activityInstanceIsLive(Activity activity) {
        activityListLock.readLock().lock();
        boolean isContains = mActivityList.contains(activity);
        activityListLock.readLock().unlock();
        return isContains;
    }

    /**
     * 指定的 {@link Activity} class 是否存活 (同一个 {@link Activity} class 可能有多个实例)。
     */
    public boolean activityClassIsLive(Class<?> activityClass) {
        activityListLock.readLock().lock();
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                activityListLock.readLock().unlock();
                return true;
            }
        }
        activityListLock.readLock().unlock();
        return false;
    }

    /**
     * 获取指定 {@link Activity} class 的实例,没有则返回 null(同一个 {@link Activity} class 有多个实例,则返回最早创建的实例)。
     */
    public Activity findActivity(Class<?> activityClass) {
        activityListLock.readLock().lock();
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                activityListLock.readLock().unlock();
                return activity;
            }
        }
        activityListLock.readLock().unlock();
        return null;
    }

    /**
     * 关闭所有 {@link Activity},排除指定的 {@link Activity}。
     *
     * @param excludeActivityClasses activity class
     */
    public void killAll(Class<?>... excludeActivityClasses) {
        List<Class<?>> excludeList = Arrays.asList(excludeActivityClasses);
        activityListLock.writeLock().lock();
            Iterator<Activity> iterator = getActivityList().iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();

                if (excludeList.contains(next.getClass())) {
                    continue;
                }

                iterator.remove();
                next.finish();
            }
        activityListLock.writeLock().unlock();
    }

}

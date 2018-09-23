package com.passin.pmvp.util;

import static com.passin.pmvp.integration.AppManager.APP_EXIT;
import static com.passin.pmvp.integration.AppManager.SHOW_SNACKBAR;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.passin.pmvp.base.delegate.IArms;
import com.passin.pmvp.di.component.ArmsComponent;
import com.passin.pmvp.integration.AppManager;
import com.passin.pmvp.integration.AppManagerEvent;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date : 2018/3/17 10:22
 * </pre>
 */

public class PmvpUtils {

    private static Application sApplication;

    private PmvpUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(@NonNull Context context) {
        PmvpUtils.sApplication = (Application) context.getApplicationContext();
    }

    public static Application getApp() {
        if (sApplication != null) {
            return sApplication;
        } else {
            throw new NullPointerException("u should init first");
        }
    }

    public static ArmsComponent obtainArmsComponentFromContext(Context context) {
        Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
        Preconditions.checkState(context.getApplicationContext() instanceof IArms,
                "Application does not implements App");
        return ((IArms) context.getApplicationContext()).getArmsComponent();
    }

    /**
     * 获得资源
     */
    public static Resources getResources() {
        return PmvpUtils.getApp().getResources();
    }

    /**
     * dip转pix
     */
    public static int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * pix转dip
     */
    public static int px2dip(int pix) {
        final float densityDpi = getResources().getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }

    /**
     * 得到字符数组
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 从 dimens 中获得尺寸
     */
    public static int getDimens(int id) {
        return (int) getResources().getDimension(id);
    }

    /**
     * 从String 中获得字符
     */
    public static String getString(int stringID) {
        return getResources().getString(stringID);
    }

    /**
     * 获得颜色
     */
    public static int getColor(int rid) {
        return getResources().getColor(rid);
    }

    /**
     * 填充view
     */
    public static View inflate(Context context, int detailScreen) {
        return View.inflate(context, detailScreen, null);
    }

    /**
     * 退出APP
     */
    public static void exitApp() {
        AppManagerEvent message = new AppManagerEvent();
        message.what = APP_EXIT;
        AppManager.post(message);
    }

    /**
     * snackbarText
     */
    public static void snackbarText(String text) {
        AppManagerEvent message = new AppManagerEvent();
        message.what = SHOW_SNACKBAR;
        message.obj = text;
        AppManager.post(message);
    }

    /**
     * snackbarText
     */
    public static void snackbarText(Context context, String text) {
        AppManagerEvent message = new AppManagerEvent();
        message.context = context;
        message.what = SHOW_SNACKBAR;
        message.obj = text;
        AppManager.post(message);
    }

    /**
     * 配置RecyclerView
     */
    public static void configRecyclerView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * View 转为 Bitmap
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     *  Bitmap 转 Drawable
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * Drawable 转 Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap
                    .createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                            Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}

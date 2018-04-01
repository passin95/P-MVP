package com.passin.pmvp.util;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.passin.pmvp.base.BaseApplication;

import butterknife.internal.Utils;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/3/21 14:22
 * </pre>
 */
public class ToastUtils {
    private static final int COLOR_DEFAULT = -16777217;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static Toast sToast;
    private static int sGravity = -1;
    private static int sXOffset = -1;
    private static int sYOffset = -1;
    private static int sBgColor = -16777217;
    private static int sBgResource = -1;
    private static int sMsgColor = -16777217;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setGravity(int gravity, int xOffset, int yOffset) {
        sGravity = gravity;
        sXOffset = xOffset;
        sYOffset = yOffset;
    }

    public static void setBgColor(@ColorInt int backgroundColor) {
        sBgColor = backgroundColor;
    }

    public static void setBgResource(@DrawableRes int bgResource) {
        sBgResource = bgResource;
    }

    public static void setMsgColor(@ColorInt int msgColor) {
        sMsgColor = msgColor;
    }

    public static void showShort(@NonNull CharSequence text) {
        if(text == null) {
            throw new NullPointerException("Argument 'text' of type CharSequence (#0 out of 1, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        } else {
            show((CharSequence)text, 0);
        }
    }

    public static void showShort(@StringRes int resId) {
        show(resId, 0);
    }

    public static void showShort(@StringRes int resId, Object... args) {
        if(args != null && args.length == 0) {
            show(resId, 0);
        } else {
            show(resId, 0, args);
        }

    }

    public static void showShort(String format, Object... args) {
        if(args != null && args.length == 0) {
            show((CharSequence)format, 0);
        } else {
            show(format, 0, args);
        }

    }

    public static void showLong(@NonNull CharSequence text) {
        if(text == null) {
            throw new NullPointerException("Argument 'text' of type CharSequence (#0 out of 1, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        } else {
            show((CharSequence)text, 1);
        }
    }

    public static void showLong(@StringRes int resId) {
        show(resId, 1);
    }

    public static void showLong(@StringRes int resId, Object... args) {
        if(args != null && args.length == 0) {
            show(resId, 0);
        } else {
            show(resId, 1, args);
        }

    }

    public static void showLong(String format, Object... args) {
        if(args != null && args.length == 0) {
            show((CharSequence)format, 0);
        } else {
            show(format, 1, args);
        }

    }

    public static View showCustomShort(@LayoutRes int layoutId) {
        View view = getView(layoutId);
        show((View)view, 0);
        return view;
    }

    public static View showCustomLong(@LayoutRes int layoutId) {
        View view = getView(layoutId);
        show((View)view, 1);
        return view;
    }

    public static void cancel() {
        if(sToast != null) {
            sToast.cancel();
            sToast = null;
        }

    }

    private static void show(@StringRes int resId, int duration) {
        show((CharSequence) BaseApplication.getApp().getResources().getText(resId).toString(), duration);
    }

    private static void show(@StringRes int resId, int duration, Object... args) {
        show((CharSequence)String.format(BaseApplication.getApp().getResources().getString(resId), args), duration);
    }

    private static void show(String format, int duration, Object... args) {
        show((CharSequence)String.format(format, args), duration);
    }

    private static void show(final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            public void run() {
                ToastUtils.cancel();
                ToastUtils.sToast = Toast.makeText(BaseApplication.getApp(), text, duration);
                TextView tvMessage = (TextView)ToastUtils.sToast.getView().findViewById(16908299);
                int msgColor = tvMessage.getCurrentTextColor();
                TextViewCompat.setTextAppearance(tvMessage, 16973886);
                if(ToastUtils.sMsgColor != -16777217) {
                    tvMessage.setTextColor(ToastUtils.sMsgColor);
                } else {
                    tvMessage.setTextColor(msgColor);
                }

                if(ToastUtils.sGravity != -1 || ToastUtils.sXOffset != -1 || ToastUtils.sYOffset != -1) {
                    ToastUtils.sToast.setGravity(ToastUtils.sGravity, ToastUtils.sXOffset, ToastUtils.sYOffset);
                }

                ToastUtils.setBg(tvMessage);
                ToastUtils.sToast.show();
            }
        });
    }

    private static void show(final View view, final int duration) {
        HANDLER.post(new Runnable() {
            public void run() {
                ToastUtils.cancel();
                ToastUtils.sToast = new Toast(BaseApplication.getApp());
                ToastUtils.sToast.setView(view);
                ToastUtils.sToast.setDuration(duration);
                if(ToastUtils.sGravity != -1 || ToastUtils.sXOffset != -1 || ToastUtils.sYOffset != -1) {
                    ToastUtils.sToast.setGravity(ToastUtils.sGravity, ToastUtils.sXOffset, ToastUtils.sYOffset);
                }

                ToastUtils.setBg();
                ToastUtils.sToast.show();
            }
        });
    }

    private static void setBg() {
        View toastView = sToast.getView();
        if(sBgResource != -1) {
            toastView.setBackgroundResource(sBgResource);
        } else if(sBgColor != -16777217) {
            Drawable background = toastView.getBackground();
            if(background != null) {
                background.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else {
                ViewCompat.setBackground(toastView, new ColorDrawable(sBgColor));
            }
        }

    }

    private static void setBg(TextView tvMsg) {
        View toastView = sToast.getView();
        if(sBgResource != -1) {
            toastView.setBackgroundResource(sBgResource);
            tvMsg.setBackgroundColor(0);
        } else if(sBgColor != -16777217) {
            Drawable tvBg = toastView.getBackground();
            Drawable msgBg = tvMsg.getBackground();
            if(tvBg != null && msgBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                tvMsg.setBackgroundColor(0);
            } else if(tvBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else if(msgBg != null) {
                msgBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else {
                toastView.setBackgroundColor(sBgColor);
            }
        }

    }

    private static View getView(@LayoutRes int layoutId) {
        LayoutInflater inflate = (LayoutInflater)BaseApplication.getApp().getSystemService("layout_inflater");
        return inflate != null?inflate.inflate(layoutId, (ViewGroup)null):null;
    }
}

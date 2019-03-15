package com.wgheng.wanandroid.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * 通过修改系统参数来适配android设备
 * <p>
 * Created by wgheng on 2018/7/11.
 */

public class DisplayAdapter {

    public static final String ADAPT_ORIENTATION_WIDTH = "width";//宽度适配策略
    public static final String ADAPT_ORIENTATION_HEIGHT = "height";//高度适配策略
    private static final float DESIGN_WIDTH = 375f;//设计宽度
    private static final float DESIGN_HEIGHT = 647f;//设计高度(不含状态栏)

    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;

    public static void setDensity(@NonNull final Application application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {
                }
            });
        }
    }

    //此方法在BaseActivity中做初始化(如果不封装BaseActivity的话,直接用下面那个方法就好)
    public static void setDefault(Activity activity) {
        setAppOrientation(activity, ADAPT_ORIENTATION_WIDTH);
    }

    //此方法用于在某一个Activity里面更改适配的方向
    public static void setOrientation(Activity activity, String adaptOrientation) {
        setAppOrientation(activity, adaptOrientation);
    }

    /**
     * targetDensity
     * targetScaledDensity
     * targetDensityDpi
     * 这三个参数是统一修改过后的值
     * <p>
     * orientation:方向值,传入width或height
     */
    private static void setAppOrientation(Activity activity, String adaptOrientation) {

        float targetDensity;

        //获取状态栏高度
        int barHeight = getStatusBarHeight(activity);
        if (adaptOrientation.equals(ADAPT_ORIENTATION_HEIGHT)) {
            targetDensity = (appDisplayMetrics.heightPixels - barHeight) / DESIGN_HEIGHT;
        } else {
            targetDensity = appDisplayMetrics.widthPixels / DESIGN_WIDTH;
        }

        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        /*
         *
         * 最后在这里将修改过后的值赋给系统参数
         *
         * 只修改Activity的density值
         */
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

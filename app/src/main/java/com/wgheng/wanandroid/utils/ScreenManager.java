package com.wgheng.wanandroid.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by wgheng on 2018/7/4.
 * Description :
 */
public class ScreenManager {

    private static volatile ScreenManager instance;

    private ScreenManager() {
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            synchronized (ScreenManager.class) {
                if (instance == null) {
                    instance = new ScreenManager();
                }
            }
        }
        return instance;
    }

    /**
     * 窗口全屏
     */
    public void setFullScreen(boolean isFullScreen, Activity mActivity) {
        if (isFullScreen) {
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * 是否竖屏
     **/
    public void setScreenOrientation(boolean isPortrait, Activity mActivity) {
        if (!isPortrait) {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}

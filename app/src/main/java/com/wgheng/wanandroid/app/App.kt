package com.wgheng.wanandroid.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.wgheng.wanandroid.utils.DisplayAdapter
import com.wgheng.wanandroid.utils.SpDelegate

/**
 * Created by wgheng on 2019/3/12.
 * Description :
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())

        SpDelegate.setContext(this)
        initDebugTools()
        initDisplayAdapter()
    }

    /**
     * 初始化调试工具
     */
    fun initDebugTools() {
        Stetho.initializeWithDefaults(this)
    }

    /**
     * 配置屏幕适配
     */
    private fun initDisplayAdapter() {
        DisplayAdapter.setDensity(this)
    }
}
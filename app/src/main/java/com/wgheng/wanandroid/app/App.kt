package com.wgheng.wanandroid.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.wgheng.wanandroid.utils.DisplayAdapter
import com.wgheng.wanandroid.utils.SpDelegate
import com.wgheng.wanandroid.widget.imageloader.GlideLoader
import com.wgheng.wanandroid.widget.imageloader.ImageLoader

/**
 * Created by wgheng on 2019/3/12.
 * Description :
 */
class App : Application() {

    companion object {
       @SuppressLint("StaticFieldLeak")
       lateinit var context:Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Logger.addLogAdapter(AndroidLogAdapter())

        SpDelegate.setContext(this)
        initDebugTools()
        initDisplayAdapter()
        initImageLoader()
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

    /**
     * 初始化图片加载库
     */
    private fun initImageLoader() {
        ImageLoader.getInstance().setGlobalImageLoader(GlideLoader())
    }

}
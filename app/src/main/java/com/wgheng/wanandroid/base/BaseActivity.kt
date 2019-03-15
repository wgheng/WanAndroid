package com.wgheng.wanandroid.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import com.gyf.barlibrary.ImmersionBar
import com.wgheng.wanandroid.R
import com.wgheng.wanandroid.utils.DisplayAdapter
import com.wgheng.wanandroid.utils.PermissionUtils
import com.wgheng.wanandroid.utils.StatusBarUtils
import com.wgheng.wanandroid.widget.loadingview.IStatusView

/**
 * Created by wgheng on 2018/7/4.
 * Description :
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var unbinder: Unbinder

    /***
     * 授权接口
     */
    private var mPermissionListener: PermissionUtils.PermissionCallbacks? = null

    /**
     * 是否使用沉浸式
     */
    protected open val isImmersionBarEnabled: Boolean
        get() = false

    protected open val statusColor: Int
        get() = R.color.colorPrimaryDark

    /**
     * 设置视图id
     */
    protected abstract val layoutId: Int

    /**
     * 获取标题栏高度（用于设置加载状态视图topMargin）
     */
    protected open val titleBarHeight: Float
        get() = resources.getDimension(R.dimen.title_bar_height)

    /**
     * 设置自定义加载状态视图
     */
    protected open val customLoadStatusView: IStatusView?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenOrientation()
        setDefaultDisplayAdaptOrientation()
        setContentView(layoutId)
        unbinder = ButterKnife.bind(this)//绑定ButterKnife
        if (isImmersionBarEnabled) {
            initImmersionBar()
        } else {
            setStatusBar()
        }
        initView()
        setListener()
        initData(savedInstanceState)
        setTitle()
    }

    /**
     * 初始化
     */
    protected open fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true, 0.2f)
            .statusBarColor(statusColor)
            .init()
    }

    /**
     * 设置状态栏（默认暗色主题）
     */
    protected open fun setStatusBar() {
        StatusBarUtils.setStatusBarLightMode(this)
        StatusBarUtils.setWindowStatusBarColor(this, statusColor)
    }

    /**
     * 设置默认屏幕适配方向（默认宽度适配，xml文件直接写设计尺寸dp为单位）
     * 可重写此方法修改页面适配方向
     */
    protected open fun setDefaultDisplayAdaptOrientation() {
        DisplayAdapter.setDefault(this)
    }

    /**
     * 设置屏幕方向（默认竖屏）
     */
    protected open fun setScreenOrientation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 初始化视图
     */
    protected open fun initView() {

    }

    /**
     * 设置view监听
     */
    protected open fun setListener() {

    }

    /**
     * 初始化数据
     */
    open fun initData(savedInstanceState: Bundle?) {

    }

    /**
     * 设置页面标题
     */
    fun setTitle() {

    }

    open fun longToast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }

    open fun shortToast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isImmersionBarEnabled) ImmersionBar.with(this).destroy()
        unbinder.unbind()
    }

    /***
     * 动态权限相关-----------------------------------------------------------------------------------
     */

    /***
     * 请求授权
     * @param permissions
     * @param listener
     */
    fun requestPermission(permissions: Array<String>, listener: PermissionUtils.PermissionCallbacks) {
        this.mPermissionListener = listener
        PermissionUtils.requestPermissions(this, REQUEST_PERMISSION_CODE, permissions, listener)

    }

    /***
     * 申请权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        /***
         * 这里因为在基类里面同意处理了权限回调，所以有可能其他第三方的权限申请也会走到这儿，
         * 如果 mPermissionListener 不判空则会引发空指针
         * 比如 融云的一些权限申请
         */
        if (mPermissionListener != null) {
            PermissionUtils.onRequestPermissionsResult(
                this,
                requestCode,
                permissions,
                grantResults,
                mPermissionListener!!
            )
        }
    }

    companion object {
        const val REQUEST_PERMISSION_CODE = 101
    }

}

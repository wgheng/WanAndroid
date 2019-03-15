package com.wgheng.wanandroid.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.wgheng.wanandroid.widget.loadingview.IStatusView
import com.wgheng.wanandroid.widget.loadingview.LoadStatusViewHolder

/**
 * Created by wgheng on 2018/7/4.
 * Description :
 */
abstract class BaseMvpActivity<T : IBasePresenter> : BaseActivity(), IBaseView {

    protected var mPresenter: T? = null
    private lateinit var mHolder: LoadStatusViewHolder

    /**
     * 设置自定义加载状态视图
     */
    override val customLoadStatusView: IStatusView?
        get() = null


    override fun initView() {
        super.initView()
        addStatusView()
    }

   override fun initData(savedInstanceState: Bundle?){
       super.initData(savedInstanceState)
       mPresenter = createPresenter()
       mPresenter?.attachView(this)
       mPresenter?.start()
   }

    /**
     * 添加loading视图
     */
    private fun addStatusView() {
        val contentRoot = findViewById<FrameLayout>(android.R.id.content)
        mHolder = LoadStatusViewHolder(this)
        val iPlaceView = customLoadStatusView
        iPlaceView?.let {
            mHolder.setCustomStatusView(it)
        }
        val statusView = mHolder.get()
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        params.topMargin = titleBarHeight.toInt()
        statusView.layoutParams = params
        contentRoot.addView(statusView)
    }

    /**
     * 创建业务逻辑对应的presenter
     */
    protected abstract fun createPresenter(): T


    /**
     * 显示/隐藏加载视图
     */
    override fun setLoadingView(isShow: Boolean) {
        if (isShow) {
            mHolder.showLoadingView()
        } else {
            mHolder.hideLoadingView()
        }
    }

    /**
     * 显示/隐藏加载失败视图
     */
    override fun setLoadFailedView(isShow: Boolean) {
        if (isShow) {
            val emptyView = mHolder.showEmptyPlaceView()
            setLoadFailedView(emptyView)
            emptyView.setOnClickListener { onClickLoadFailedView() }
        } else {
            mHolder.hideEmptyPlaceView()
        }
    }

    /**
     * 重写此方法加载失败的视图
     */
    protected open fun setLoadFailedView(failedView: View) {

    }

    /**
     * 显示/隐藏空数据视图
     */
    override fun setNoDataView(isShow: Boolean) {
        if (isShow) {
            val emptyView = mHolder.showEmptyPlaceView()
            setNoDataView(emptyView)
        } else {
            mHolder.hideEmptyPlaceView()
        }
    }

    /**
     * 重写此方法设置空数据时视图
     */
    protected open fun setNoDataView(emptyView: View) {

    }

    /**
     * 点击加载失败页面处理
     */
    protected open fun onClickLoadFailedView() {

    }

    override fun longToast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }

    override fun shortToast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

}

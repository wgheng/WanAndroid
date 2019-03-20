package com.wgheng.wanandroid.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import com.wgheng.wanandroid.widget.loadingview.IStatusView
import com.wgheng.wanandroid.widget.loadingview.LoadStatusViewHolder

/**
 * Created by wgheng on 2018/7/10.
 * Description :
 */
abstract class BaseFragment<T : IBasePresenter> : Fragment(), IBaseView {

    protected var mPresenter: T? = null
    private lateinit var unbinder: Unbinder
    private lateinit var mHolder: LoadStatusViewHolder
    protected lateinit var mContext: Context

    /**
     * 设置视图id
     */
    protected abstract val layoutId: Int

    /**
     * 设置自定义加载状态视图
     */
    protected val customLoadStatusView: IStatusView?
        get() = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = FrameLayout(mContext)
        val view = inflater.inflate(layoutId, rootView, false)
        rootView.addView(view)
        addStatusView(rootView)
        unbinder = ButterKnife.bind(this, rootView)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        setListener()
        mPresenter = createPresenter()
        mPresenter?.attachView(this)//初始化数据
        mPresenter?.start()
        initData()
        setTitle()
    }

    /**
     * 创建模块对应的Presenter
     */
    protected abstract fun createPresenter(): T

    /**
     * 添加loading视图
     */
    private fun addStatusView(container: ViewGroup) {
        mHolder = LoadStatusViewHolder(mContext)
        val iPlaceView = customLoadStatusView
        iPlaceView?.let {
            mHolder.setCustomStatusView(it)
        }
        container.addView(mHolder.get())
    }

    protected open fun initView() {

    }

    /**
     * 设置view监听
     */
    protected open fun setListener() {

    }

    protected open fun initData() {

    }

    /**
     * 设置页面标题
     */
    protected open fun setTitle() {

    }

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
    protected fun setLoadFailedView(failedView: View) {

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
    protected fun setNoDataView(emptyView: View) {

    }

    /**
     * 点击加载失败页面处理
     */
    protected fun onClickLoadFailedView() {

    }

    override fun longToast(content: String) {
        Toast.makeText(mContext, content, Toast.LENGTH_LONG).show()
    }

    override fun shortToast(content: String) {
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
        mPresenter?.detachView()
    }
}

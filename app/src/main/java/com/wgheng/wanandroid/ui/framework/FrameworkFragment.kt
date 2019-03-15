package com.wgheng.wanandroid.ui.framework

import com.wgheng.wanandroid.R
import com.wgheng.wanandroid.base.BaseFragment

/**
 * Created by wgheng on 2019/3/15.
 * Description :
 */
class FrameworkFragment : BaseFragment<FrameworkContract.Presenter>(), FrameworkContract.View {

    override val layoutId: Int
        get() = R.layout.fragment_framework

    override fun createPresenter(): FrameworkContract.Presenter {
        return FrameworkPresenter()
    }


}
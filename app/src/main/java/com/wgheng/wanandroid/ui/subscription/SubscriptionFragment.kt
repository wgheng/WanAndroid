package com.wgheng.wanandroid.ui.subscription

import com.wgheng.wanandroid.R
import com.wgheng.wanandroid.base.BaseFragment

/**
 * Created by wgheng on 2019/3/15.
 * Description :
 */
class SubscriptionFragment : BaseFragment<SubscriptionContract.Presenter>(), SubscriptionContract.View {

    override val layoutId: Int
        get() = R.layout.fragment_subscription

    override fun createPresenter(): SubscriptionContract.Presenter {
        return SubscriptionPresenter()
    }

    fun refresh() {

    }


}
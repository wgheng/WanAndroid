package com.wgheng.wanandroid.base

/**
 * Created by wgheng on 2019/3/14.
 * Description :
 */
interface IBaseView {
    fun setLoadingView(isShow: Boolean)
    fun setLoadFailedView(isShow: Boolean)
    fun setNoDataView(isShow: Boolean)
    fun longToast(content: String)
    fun shortToast(content: String)
}
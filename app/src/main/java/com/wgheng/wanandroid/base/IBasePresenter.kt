package com.wgheng.wanandroid.base

/**
 * Created by wgheng on 2019/3/14.
 * Description :
 */
interface IBasePresenter {

    fun attachView(view: IBaseView)

    fun start()

    fun detachView()

}
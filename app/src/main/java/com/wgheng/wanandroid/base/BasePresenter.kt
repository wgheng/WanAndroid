package com.wgheng.wanandroid.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by wgheng on 2019/3/14.
 * Description :
 */
open class BasePresenter<T : IBaseView> : IBasePresenter {
    override fun start() {

    }


    var mView: T? = null
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: IBaseView) {
        @Suppress("UNCHECKED_CAST")
        this.mView = view as T
    }


    fun addSubscribe(disposable: CompositeDisposable) {
        mCompositeDisposable.add(disposable)
    }

    override fun detachView() {
        mView = null
        unSubscribe()
    }

    private fun unSubscribe() {
        mCompositeDisposable.dispose()
    }
}
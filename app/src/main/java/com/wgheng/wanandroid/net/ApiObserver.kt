package com.wgheng.wanandroid.net

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by wgheng on 2019/3/14.
 * Description :
 */
abstract class ApiObserver<T> : Observer<HttpResponse<T>> {



    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: HttpResponse<T>) {
        when (t.errorCode) {
            0 -> doOnSuccess(t.data, t.errorMsg)
            else -> handleFailureResult(t.errorCode, t.errorMsg)
        }
    }

    private fun handleFailureResult(errorCode: Int, errorMsg: String?) {
        when(errorCode){
            -1001 -> onLoginError()
            else -> doOnFailure(errorCode, errorMsg)
        }
    }

    private fun onLoginError() {

    }

    protected  fun doOnFailure(errorCode: Int, errorMsg: String?){

    }

    protected abstract fun doOnSuccess(data: T?, msg: String?)

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {

    }


}
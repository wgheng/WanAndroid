package com.wgheng.wanandroid.net

import com.wgheng.wanandroid.model.bean.ArticleListBean
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by wgheng on 2019/3/15.
 * Description :
 */
class RemoteRepository private constructor() {

    companion object {
        val instance = SingleInstance.holder
    }

    private object SingleInstance {
        val holder = RemoteRepository()
    }

    fun <T> switchThread(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun getArticleList(page: Int): Observable<HttpResponse<ArticleListBean>> {
        return HttpClient.getInstance()
            .getApiService()
            .getArticleList(page)
            .compose(switchThread())
    }


}

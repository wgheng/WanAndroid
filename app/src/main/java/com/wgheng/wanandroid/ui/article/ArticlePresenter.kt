package com.wgheng.wanandroid.ui.article

import com.orhanobut.logger.Logger
import com.wgheng.wanandroid.base.BasePresenter
import com.wgheng.wanandroid.model.bean.ArticleListBean
import com.wgheng.wanandroid.net.ApiObserver
import com.wgheng.wanandroid.net.DataManager

/**
 * Created by wgheng on 2019/3/15.
 * Description :
 */
class ArticlePresenter : BasePresenter<ArticleContract.View>(), ArticleContract.Presenter {

    var page = 0

    override fun start() {
        getArticle(page)
    }

    fun getArticle(page: Int) {
        DataManager
            .remoteRepository()
            .getArticleList(page)
            .subscribe(object : ApiObserver<ArticleListBean>() {
                override fun doOnSuccess(data: ArticleListBean?, msg: String?) {
                    Logger.i(data?.toString()?:"")
                }
            })
    }

}
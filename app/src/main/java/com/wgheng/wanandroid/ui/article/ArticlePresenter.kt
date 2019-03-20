package com.wgheng.wanandroid.ui.article

import com.wgheng.wanandroid.base.BasePresenter
import com.wgheng.wanandroid.model.bean.ArticleListBean
import com.wgheng.wanandroid.net.ApiObserver
import com.wgheng.wanandroid.net.DataManager

/**
 * Created by wgheng on 2019/3/15.
 * Description :
 */
class ArticlePresenter : BasePresenter<ArticleContract.View>(), ArticleContract.Presenter {

    companion object {
        const val INIT_PAGE = 0
    }

    private var page = INIT_PAGE

    override fun start() {
        refresh()
    }

    private fun getArticle(page: Int) {
        DataManager
            .remoteRepository()
            .getArticleList(page)
            .subscribe(object : ApiObserver<ArticleListBean>() {
                override fun doOnSuccess(data: ArticleListBean?, msg: String?) {
                    data?.apply {
                        //curPage从1开始
                        if (page == INIT_PAGE) {
                            mView?.showArticleList(data)
                        } else {
                            mView?.loadMore(data)
                            if (curPage == pageCount) {
                                mView?.onNoMoreData()
                            }
                        }
                    }
                }
            })
    }

    override fun refresh() {
        page = 0
        getArticle(page)
    }

    override fun loadMore() {
        getArticle(page++)
    }

}
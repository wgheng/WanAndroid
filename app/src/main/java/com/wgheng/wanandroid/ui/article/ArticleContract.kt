package com.wgheng.wanandroid.ui.article

import com.wgheng.wanandroid.base.IBasePresenter
import com.wgheng.wanandroid.base.IBaseView
import com.wgheng.wanandroid.model.bean.ArticleListBean

/**
 * Created by wgheng on 2019/3/15.
 * Description :
 */
interface ArticleContract {
    interface View : IBaseView {
        fun showArticleList(data: ArticleListBean)
        fun loadMore(data: ArticleListBean)
        fun onNoMoreData()
    }

    interface Presenter : IBasePresenter {
        fun refresh()
        fun loadMore()
    }
}
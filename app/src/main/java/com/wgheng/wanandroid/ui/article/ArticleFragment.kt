package com.wgheng.wanandroid.ui.article

import com.wgheng.wanandroid.R
import com.wgheng.wanandroid.base.BaseFragment

/**
 * Created by wgheng on 2019/3/15.
 * Description :
 */
class ArticleFragment : BaseFragment<ArticleContract.Presenter>(),ArticleContract.View{
    override val layoutId: Int
        get() = R.layout.fragment_article

    override fun createPresenter(): ArticleContract.Presenter {
        return ArticlePresenter()
    }



}
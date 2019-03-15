package com.wgheng.wanandroid.ui.main

import com.wgheng.wanandroid.R
import com.wgheng.wanandroid.base.BaseActivity
import com.wgheng.wanandroid.ui.article.ArticleFragment

class MainActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView() {
        super.initView()

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.flMain, ArticleFragment())
        ft.commit()

    }
}

package com.wgheng.wanandroid.ui.main

import android.support.v4.app.Fragment
import com.wgheng.wanandroid.R
import com.wgheng.wanandroid.base.BaseActivity
import com.wgheng.wanandroid.ui.article.ArticleFragment
import com.wgheng.wanandroid.ui.subscription.SubscriptionFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    var position: Int = 0
    private lateinit var fragments: ArrayList<Fragment>

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView() {
        super.initView()
        fragments = ArrayList()
        fragments.add(ArticleFragment())
        fragments.add(SubscriptionFragment())
        switchFragment(position)
    }

    override fun setListener() {
        super.setListener()
        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> position = 0
                R.id.navSubscribe -> position = 1
            }
            switchFragment(position)
            true
        }
        navView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.navHome -> (fragments.get(position) as ArticleFragment).refresh()
                R.id.navSubscribe -> (fragments.get(position) as SubscriptionFragment).refresh()
            }
        }
    }

    private fun switchFragment(position: Int) {
        val ft = supportFragmentManager.beginTransaction()
        val fragment = fragments.get(position)
        fragments.forEach {
            if (it.isAdded) {
                ft.hide(it)
            }
        }
        if (fragment.isAdded) {
            ft.show(fragment)
        } else {
            ft.add(R.id.flMain, fragment)
        }
        ft.commit()
    }
}

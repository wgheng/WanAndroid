package com.wgheng.wanandroid.ui.article

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wgheng.wanandroid.R
import com.wgheng.wanandroid.base.BaseFragment
import com.wgheng.wanandroid.model.bean.ArticleListBean
import com.wgheng.wanandroid.utils.DensityUtil
import kotlinx.android.synthetic.main.fragment_article.*

/**
 * Created by wgheng on 2019/3/15.
 * Description :
 */
class ArticleFragment : BaseFragment<ArticleContract.Presenter>(), ArticleContract.View {

    private lateinit var mAdapter: ArticleAdapter

    override val layoutId: Int
        get() = R.layout.fragment_article

    override fun initView() {
        super.initView()
        mAdapter = ArticleAdapter(R.layout.item_article)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.addItemDecoration(object: RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.top  = DensityUtil.dp2px(mContext,5f)
                outRect.bottom  = DensityUtil.dp2px(mContext,5f)
                outRect.left =DensityUtil.dp2px(mContext,10f)
                outRect.right =DensityUtil.dp2px(mContext,10f)
            }
        })
    }

    override fun createPresenter(): ArticleContract.Presenter {
        return ArticlePresenter()
    }

    fun refresh() {
        mPresenter?.refresh()
    }

    override fun showArticleList(data: ArticleListBean) {
        mAdapter.refresh(data.datas)
    }

    override fun loadMore(data: ArticleListBean) {

    }

    override fun onNoMoreData() {

    }

}
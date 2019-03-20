package com.wgheng.wanandroid.ui.article

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wgheng.wanandroid.R
import com.wgheng.wanandroid.model.bean.ArticleListBean
import com.wgheng.wanandroid.widget.imageloader.ImageLoader
import kotlinx.android.synthetic.main.item_article.view.*

/**
 * Created by wgheng on 2019/3/18.
 * Description :
 */
class ArticleAdapter(layoutResId: Int) : BaseQuickAdapter<ArticleListBean.Data, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: ArticleListBean.Data?) {
        item?.apply {
            helper?.let {
                it.setText(R.id.tvTitle, title)
                    .setText(R.id.tvDesc, desc)
                    .setText(R.id.tvAuthor, author)
                    .setText(R.id.tvTime, niceDate)

                val descContainView = it.getView<View>(R.id.descContainerView)
                if (desc.isNotEmpty()) {
                    descContainView.visibility = View.VISIBLE
                    ImageLoader.getInstance()
                        .load(envelopePic)
                        .with(mContext)
                        .into(it.itemView.ivImage)
                } else {
                    descContainView.visibility = View.GONE
                }
            }
        }
    }

    fun refresh(datas: List<ArticleListBean.Data>) {
        setNewData(datas)
    }

}
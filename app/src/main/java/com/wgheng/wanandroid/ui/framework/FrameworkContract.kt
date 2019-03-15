package com.wgheng.wanandroid.ui.framework

import com.wgheng.wanandroid.base.IBasePresenter
import com.wgheng.wanandroid.base.IBaseView

/**
 * Created by wgheng on 2019/3/15.
 * Description :
 */
interface FrameworkContract {
    interface View :IBaseView
    interface Presenter:IBasePresenter
}
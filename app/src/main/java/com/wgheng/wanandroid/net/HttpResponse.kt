package com.wgheng.wanandroid.net

/**
 * Created by wgheng on 2019/3/14.
 * Description :
 */
data class HttpResponse<T>(

    var errorCode: Int,
    var errorMsg: String? = "",
    var data: T?

)
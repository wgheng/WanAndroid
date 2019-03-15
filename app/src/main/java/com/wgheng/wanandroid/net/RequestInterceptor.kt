package com.wgheng.wanandroid.net

import com.wgheng.wanandroid.app.Constant
import com.wgheng.wanandroid.utils.SpDelegate
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by wgheng on 2019/3/13.
 * Description :
 */
class RequestInterceptor :Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newBuilder = request.newBuilder()
        val host = request.url().host()
        //接口统一添加cookie
        val cookie by SpDelegate(host,"")
        if (cookie.isNotEmpty()) {
            newBuilder.addHeader(Constant.HEADER_COOKIE, cookie)
        }
       return chain.proceed(newBuilder.build())
    }

}
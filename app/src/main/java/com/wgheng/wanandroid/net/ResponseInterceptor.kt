package com.wgheng.wanandroid.net

import com.wgheng.wanandroid.app.Constant
import com.wgheng.wanandroid.utils.SpDelegate
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by wgheng on 2019/3/13.
 * Description :
 */
class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        val host = request.url().host()
        val response = chain.proceed(request)
        //注册和登录接口保存token
        if (url.contains(Constant.KEY_LOGIN)
            || url.contains(Constant.KEY_REGISTER)
            && !request.headers(Constant.SET_COOKIE).isEmpty()
        ) {
            val sb = StringBuilder()
            for (header in response.headers(Constant.SET_COOKIE)) {
                sb.append(header).append(";")
            }
            val index = sb.lastIndexOf(";")
            sb.deleteCharAt(index)
            @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
            var cookie: String by SpDelegate(host, "")
            @Suppress("UNUSED_VALUE")
            cookie = sb.toString()
        }
        return response
    }
}
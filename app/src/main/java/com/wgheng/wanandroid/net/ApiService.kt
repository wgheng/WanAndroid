package com.wgheng.wanandroid.net

import com.wgheng.wanandroid.model.bean.ArticleListBean
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by wgheng on 2019/3/12.
 * Description :
 */
interface ApiService {

    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Observable<HttpResponse<Any>>

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<HttpResponse<Any>>

    /**
     * 获取首页文章列表
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int = 0): Observable<HttpResponse<ArticleListBean>>
}
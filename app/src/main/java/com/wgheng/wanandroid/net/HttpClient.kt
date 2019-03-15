package com.wgheng.wanandroid.net

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.wgheng.wanandroid.app.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by wgheng on 2019/3/12.
 * Description :
 */
class HttpClient {

    private var mApiService:ApiService

    init {
        val client = OkHttpClient
            .Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(ResponseInterceptor())
            .addInterceptor(HttpLogInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        val retrofit = Retrofit
            .Builder()
            .baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        mApiService =  retrofit.create(ApiService::class.java)

    }

    fun getApiService(): ApiService {
        return mApiService
    }

    companion object {
        @Volatile
        private var instance: HttpClient? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: HttpClient().also { instance = it }
        }
    }
}
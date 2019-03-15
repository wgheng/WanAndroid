package com.wgheng.wanandroid.net

/**
 * Created by wgheng on 2019/3/15.
 * Description :
 */
class DataManager{

    companion object {
        fun remoteRepository():RemoteRepository = RemoteRepository.instance
    }
}
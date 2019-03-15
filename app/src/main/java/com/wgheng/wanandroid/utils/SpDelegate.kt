package com.wgheng.wanandroid.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by wgheng on 2019/3/13.
 * Description :
 */
class SpDelegate<T>(val key: String, val defaultValue: T) : ReadWriteProperty<Any?, T> {

    companion object {
        lateinit var sp: SharedPreferences

        fun setContext(context: Context) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE)
        }

        fun clear() {
            sp.edit().clear().apply()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
      val value : Any = with(sp) {
            when (defaultValue) {
                is Int -> getInt(key,defaultValue)
                is Long -> getLong(key,defaultValue)
                is Boolean -> getBoolean(key,defaultValue)
                is Float -> getFloat(key,defaultValue)
                is String -> getString(key,defaultValue)
                else -> throw IllegalArgumentException("不能保存此类型的数据")
            }
        }
       return value as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        with(sp.edit()) {
            when (value) {
                is Int -> putInt(key,value)
                is Long -> putLong(key,value)
                is Boolean -> putBoolean(key,value)
                is Float -> putFloat(key,value)
                is String -> putString(key,value)
                else -> throw IllegalArgumentException("不能保存此类型的数据")
            }
            apply()
        }
    }
}
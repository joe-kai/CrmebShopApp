package com.joekay.module_base.utils

import android.os.Parcelable
import android.text.TextUtils
import com.tencent.mmkv.MMKV

/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：
 */
var mmkv: MMKV = MMKV.defaultMMKV()
object MMKVUtils {
    /**
     * 存储常规数据
     */
    fun put(key: String, content: Any?): Boolean {
        if (TextUtils.isEmpty(key) || content == null) return false
        try {
            when (content) {
                is String -> {
                    mmkv.encode(key, content)
                }
                is Double -> {
                    mmkv.encode(key, content)
                }
                is Float -> {
                    mmkv.encode(key, content)
                }
                is Int -> {
                    mmkv.encode(key, content)
                }
                is Parcelable -> {
                    mmkv.encode(key, content)
                }
                is ByteArray -> {
                    mmkv.encode(key, content)
                }
                is Long -> {
                    mmkv.encode(key, content)
                }
                is Boolean -> {
                    mmkv.encode(key, content)
                }
                else -> {
                    return false
                }
            }
        } catch (exception: Exception) {
            return false
        }
        return true
    }

    /**
     * 存储set集合
     */
    fun putSet(key: String, content: Set<String>): Boolean {
        if (TextUtils.isEmpty(key)) return false
        mmkv.encode(key, content)
        return true
    }

    /**
     * 获取String 数据
     */
    fun getString(key: String): String {
        return mmkv.decodeString(key) ?: ""
    }

    fun getString(key: String, defult: String): String {
        if (TextUtils.isEmpty(mmkv.decodeString(key))) return defult
        return mmkv.decodeString(key) ?: ""
    }

    fun getDouble(key: String): Double {
        return mmkv.decodeDouble(key)
    }

    fun getDouble(key: String, defult: Double): Double {
        if (mmkv.decodeDouble(key) == 0.0) return defult
        return mmkv.decodeDouble(key)
    }

    fun getFloat(key: String): Float {
        return mmkv.decodeFloat(key)
    }

    fun getFloat(key: String, defult: Float): Float {
        if (mmkv.decodeDouble(key) == 0.0) return defult
        return mmkv.decodeFloat(key)
    }

    fun getInt(key: String): Int {
        return mmkv.decodeInt(key)
    }

    fun getInt(key: String, defult: Int): Int {
        if (mmkv.decodeInt(key) == 0) return defult
        return mmkv.decodeInt(key)
    }

    fun getByteArray(key: String): ByteArray? {
        return mmkv.decodeBytes(key)
    }

    fun getByteArray(key: String, default: Int): ByteArray? {
        return mmkv.decodeBytes(key)
    }

    fun getParcelable(key: String, clazz: Class<Parcelable>): Parcelable? {
        return mmkv.decodeParcelable(key, clazz)
    }

    fun getSet(key: String): Set<String>? {
        return mmkv.decodeStringSet(key)
    }

    //   删除数据
    fun remove(key: String) {
        mmkv.removeValueForKey(key)
    }

    // 删除多个数据
    fun remove(vararg key: String) {
        val keys = arrayOfNulls<String>(key.size)
        key.forEachIndexed { index, content ->
            keys.set(index, content)
        }
        mmkv.removeValuesForKeys(keys)
    }

    // 清理所有数据
    fun clearAll() {
        mmkv.clearAll()
    }

}
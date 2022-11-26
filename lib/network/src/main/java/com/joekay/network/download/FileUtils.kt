package com.joekay.network.download

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import okhttp3.RequestBody
import okio.source
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.URLEncoder

/**
 * @author:  JoeKai
 * @date:  2022/11/25
 * @explain：
 */
object FileUtils {
    private val HANDLER = Handler(Looper.getMainLooper())

    /**
     * 在主线程中执行
     */
    fun post(r: Runnable) {
        HANDLER.post(r)
    }

    /**
     * 延迟一段时间执行
     */
    fun postDelayed(r: Runnable, delayMillis: Long) {
     HANDLER.postDelayed(r, delayMillis)
    }

    /**
     * 关闭流
     */
    fun closeStream(closeable: Closeable?) {
        if (closeable == null) {
            return
        }
        try {
            closeable.close()
        } catch (e: Exception) {
            //EasyLog.print(e)
        }
    }

    /**
     * 判断对象是否为 Bean 类
     */
    fun isBeanType(`object`: Any?): Boolean {
        return if (`object` == null) {
            false
        } else !(`object` is Number || `object` is CharSequence || `object` is Boolean ||
                `object` is File || `object` is InputStream || `object` is RequestBody ||
                `object` is Char || `object` is JSONObject || `object` is JSONArray)
        // Number：Long、Integer、Short、Double、Float、Byte
        // CharSequence：String、StringBuilder、StringBuilder
    }





    /**
     * 判断对象或者集合是否为空
     */
    fun isEmpty(`object`: Any?): Boolean {
        if (`object` == null) {
            return true
        }
        return if (`object` is List<*> && `object`.isEmpty()) {
            true
        } else `object` is Map<*, *> && `object`.isEmpty()
    }







    /**
     * 获取对象反射类型
     */
    fun getReflectType(`object`: Any?): Type? {
        if (`object` == null) {
            return Void::class.java
        }
        val types = `object`.javaClass.genericInterfaces
        return if (types.size > 0) {
            // 如果这个监听对象是直接实现了接口
            (types[0] as ParameterizedType).actualTypeArguments[0]
        } else (`object`.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]

        // 如果这个监听对象是通过类继承
    }

    /**
     * 获取进度百分比
     */
    fun getProgressProgress(totalByte: Long, currentByte: Long): Int {
        // 计算百分比，这里踩了两个坑
        // 当文件很大的时候：字节数 * 100 会超过 int 最大值，计算结果会变成负数
        // 还有需要注意的是，long 除以 long 等于 long，这里的字节数除以总字节数应该要 double 类型的
        return (currentByte.toDouble() / totalByte * 100).toInt()
    }

    /**
     * 字符串编码
     */
    fun encodeString(text: String?): String? {
        return if (TextUtils.isEmpty(text)) {
            ""
        } else try {
            URLEncoder.encode(text, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            text
        }
    }


}
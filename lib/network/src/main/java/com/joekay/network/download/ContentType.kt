package com.joekay.network.download

import android.text.TextUtils
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.net.URLConnection

/**
 * @author:  JoeKai
 * @date:  2022/11/25
 * @explain：
 */
class ContentType {
    companion object{
        /** 字节流  */
        val STREAM = "application/octet-stream".toMediaTypeOrNull()

        /** Json  */
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

        /** 纯文本  */
        val TEXT = "text/plain; charset=utf-8".toMediaTypeOrNull()

        /**
         * 根据文件名获取 MIME 类型
         */
        fun guessMimeType(fileName: String): MediaType? {
            var fileName = fileName
            if (TextUtils.isEmpty(fileName)) {
                return STREAM
            }
            val fileNameMap = URLConnection.getFileNameMap()
            // 解决文件名中含有#号异常的问题
            fileName = fileName.replace("#", "")
            val contentType = fileNameMap.getContentTypeFor(fileName) ?: return STREAM
            var type = contentType.toMediaTypeOrNull()
            if (type == null) {
                type = STREAM
            }
            return type
        }
    }

}
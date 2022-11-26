package com.joekay.network.download

import android.text.TextUtils
import android.util.Log
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

/**
 * @author:  JoeKai
 * @date:  2022/11/25
 * @explain：
 */
open class DownloadCallback : BaseCallback() {


    /** 文件 MD5 正则表达式  */
    private val FILE_MD5_REGEX = "^[\\w]{32}$"

    /** 保存的文件  */
    private var mFile: FileWrapper? = null

    /** 校验的 MD5  */
    private var mMd5: String? = null

    /** 下载监听回调  */
    private var mListener: OnDownloadListener? = null

    /** 下载总字节  */
    private var mTotalByte: Long = 0

    /** 已下载字节  */
    private var mDownloadByte: Long = 0

    /** 下载进度  */
    private var mDownloadProgress = 0
    fun setFile(file: FileWrapper): DownloadCallback {
        mFile = file
        return this
    }

    fun setMd5(md5: String): DownloadCallback {
        mMd5 = md5
        return this
    }

    fun setListener(listener: OnDownloadListener): DownloadCallback {
        mListener = listener
        return this
    }


    override fun onResponse(response: Response) {
        // 如果没有指定文件的 md5 值
        if (mMd5 == null) {
            // 获取响应头中的文件 MD5 值
            val md5 = response.header("Content-MD5")
            // 这个 md5 值必须是文件的 md5 值
            if (!TextUtils.isEmpty(md5) && md5!!.matches(Regex(FILE_MD5_REGEX))) {
                mMd5 = md5
            }
        }

        val parentFile = mFile!!.parentFile
        if (parentFile != null) {
            FileWrapper.createFolder(parentFile)
        }
        val body = response.body ?: throw RuntimeException("The response body is empty")
        mTotalByte = body.contentLength()
        if (mTotalByte < 0) {
            mTotalByte = 0
        }

        // 如果这个文件已经下载过，并且经过校验 MD5 是同一个文件的话，就直接回调下载成功监听
        if (!TextUtils.isEmpty(mMd5) && mFile!!.isFile &&
            mMd5.equals(FileWrapper.getFileMd5(mFile!!.openInputStream()), ignoreCase = true)
        ) {
            FileUtils.post {
                if (mListener == null) {
                    return@post
                }
                mListener!!.onComplete(mFile!!)
                mListener!!.onEnd(mFile!!)
            }
            return
        }

        var readLength: Int
        mDownloadByte = 0
        val bytes = ByteArray(8192)
        val inputStream = body.byteStream()
        val outputStream = mFile!!.openOutputStream()
        while (inputStream.read(bytes).also { readLength = it } != -1) {
            mDownloadByte += readLength.toLong()
            outputStream!!.write(bytes, 0, readLength)
            FileUtils.post {
                if (mListener == null) {
                    return@post
                }
                mListener!!.onByte(mFile!!, mTotalByte, mDownloadByte)
                val progress: Int = FileUtils.getProgressProgress(mTotalByte, mDownloadByte)
                // 只有下载进度发生改变的时候才回调此方法，避免引起不必要的 View 重绘
                if (progress != mDownloadProgress) {
                    mDownloadProgress = progress
                    mListener!!.onProgress(mFile!!, mDownloadProgress)
                    Log.d(
                        "--DownloadCallback",
                        mFile!!.path + " 正在下载，总字节：" + mTotalByte +
                                "，已下载：" + mDownloadByte + "，进度：" + progress + " %"
                    )
                }
            }
        }
        FileUtils.closeStream(inputStream)
        FileUtils.closeStream(outputStream)

        val md5 = FileWrapper.getFileMd5(mFile!!.openInputStream())
        if (!TextUtils.isEmpty(mMd5) && !mMd5.equals(md5, ignoreCase = true)) {
            // 文件 MD5 值校验失败
            throw RuntimeException("MD5 verify failure  ${md5}")
        }

        FileUtils.post {
            if (mListener == null) {
                return@post
            }
            mListener!!.onComplete(mFile!!)
            mListener!!.onEnd(mFile!!)
        }
    }

    override fun onFailure(e: Exception) {
        FileUtils.post {
            if (mListener == null) {
                return@post
            }
            mListener!!.onError(mFile!!, e)
            mListener!!.onEnd(mFile!!)
        }
    }

    override fun onStart(call: Call) {
        FileUtils.post {
            if (mListener == null) {
                return@post
            }
            mListener!!.onStart(mFile!!)
        }
    }

}



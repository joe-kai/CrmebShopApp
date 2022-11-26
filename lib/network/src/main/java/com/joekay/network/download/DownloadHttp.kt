package com.joekay.network.download

import android.content.ContentResolver
import android.net.Uri
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

/**
 * @author:  JoeKai
 * @date:  2022/11/25
 * @explain：
 */
object DownloadHttp {

    /** 校验的 md5  */
    private var mUrl: String = ""

    /** 保存的文件  */
    private lateinit var mFile: FileWrapper

    /** 校验的 md5  */
    private lateinit var mMd5: String

    /** 下载监听回调  */
    private lateinit var mListener: OnDownloadListener

    /** 请求执行对象  */
    private lateinit var mCallProxy: CallProxy

    /**
     * 设置下载地址
     */
    fun url(url: String) =apply {
        mUrl = url
    }

    fun file(path: String)  =apply {
        file(File(path))
    }

    fun file(file: File?)  =apply {
        if (file is FileContentResolver) {
            file(file)
        } else {
            mFile = FileWrapper(file!!)
        }
    }

    fun file(resolver: ContentResolver, uri: Uri) =apply {
        file(FileContentResolver(resolver, uri))

    }

    fun file(file: FileContentResolver) =apply{
        mFile = file
    }

    /**
     * 设置 MD5 值
     */
    fun md5(md5: String)=apply{
        mMd5 = md5
    }

    /**
     * 设置下载监听
     */
    fun listener(listener: OnDownloadListener) =apply {
        mListener = listener
    }


    fun start(): DownloadHttp {
        val request = Request.Builder().url(mUrl).build()
        val call = OkHttpClient().newCall(request)
        mCallProxy = CallProxy(call)

        FileUtils.postDelayed({
            DownloadCallback()
                .setFile(mFile)
                .setMd5(mMd5)
                .setListener(mListener)
                .setCall(mCallProxy)
                .start()
        }, 1L)

        return this

    }


}
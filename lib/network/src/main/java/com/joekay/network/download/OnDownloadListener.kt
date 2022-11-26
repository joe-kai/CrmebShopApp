package com.joekay.network.download

import java.io.File

/**
 * @author:  JoeKai
 * @date:  2022/11/25
 * @explain：下载监听器
 */
interface OnDownloadListener {
    fun onStart(file: File)

    /**
     * 下载字节改变
     *
     * @param totalByte             总字节数
     * @param downloadByte          已下载字节数
     */
    fun onByte(file: File, totalByte: Long, downloadByte: Long) {}

    /**
     * 下载进度改变
     *
     * @param progress              下载进度值（0-100）
     */
    fun onProgress(file: File, progress: Int)

    /**
     * 下载完成
     */
    fun onComplete(file: File)

    /**
     * 下载出错
     */
    fun onError(file: File, e: Exception)

    /**
     * 下载结束
     */
    fun onEnd(file: File)
}
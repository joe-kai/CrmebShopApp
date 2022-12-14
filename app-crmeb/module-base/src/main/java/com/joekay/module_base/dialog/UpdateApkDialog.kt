package com.joekay.module_base.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.core.content.FileProvider
import com.joekay.base.ActivityManager
import com.joekay.base.R
import com.joekay.base.action.AnimAction
import com.joekay.base.dialog.BaseDialog
import com.joekay.base.utils.GlobalUtil
import com.joekay.base.widgets.ProgressButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import zlc.season.rxdownload4.download
import zlc.season.rxdownload4.file
import java.io.File

/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：
 */
class UpdateApkDialog {
    class Builder(context: Context) : BaseDialog.Builder<Builder>(context) {
        private val nameView: TextView? by lazy { findViewById(R.id.tv_update_name) }
        private val detailsView: TextView? by lazy { findViewById(R.id.tv_update_details) }
        private val btnUpdate: ProgressButton? by lazy { findViewById(R.id.btn_update) }
        private val closeView: TextView? by lazy { findViewById(R.id.tv_update_close) }

        /** Apk 文件 */
        private var apkFile: File? = null

        /** 下载地址 */
        private lateinit var downloadUrl: String

        /** 是否强制更新 */
        private var forceUpdate = false

        private var state = NORMAL

        companion object {
            const val NORMAL = 0 //默认
            const val STARTED = 1//下载中
            const val PAUSED = 2
            const val COMPLETED = 3//下载完成
            const val FAILED = 4//下载失败
        }

        init {
            setContentView(R.layout.update_apk_dialog)
            setAnimStyle(AnimAction.ANIM_BOTTOM)
            setCancelable(false)
            setOnClickListener(btnUpdate, closeView)
            // 让 TextView 支持滚动
            detailsView?.movementMethod = ScrollingMovementMethod()
        }

        /**
         * 设置版本名
         */
        fun setVersionName(name: CharSequence?): Builder = apply {
            nameView?.text = name
        }

        /**
         * 设置更新日志
         */
        fun setUpdateLog(text: CharSequence?): Builder = apply {
            detailsView?.text = text
            detailsView?.visibility = if (text == null) View.GONE else View.VISIBLE
        }

        /**
         * 设置强制更新
         */
        fun setForceUpdate(force: Boolean): Builder = apply {
            forceUpdate = force
            closeView?.visibility = if (force) View.GONE else View.VISIBLE
            setCancelable(!force)
        }

        /**
         * 设置下载 url
         */
        fun setDownloadUrl(url: String): Builder = apply {
            downloadUrl = url
        }

        override fun onClick(view: View) {
            if (view === closeView) {
                dismiss()
            } else if (view === btnUpdate) {
                when (state) {
                    NORMAL -> downloadApk()
                    PAUSED -> downloadApk()
                    COMPLETED -> installApk()
                    FAILED -> downloadApk()
                }
            }
        }

        /**
         * 下载 Apk
         */
        private fun downloadApk() {
            // 设置对话框不能被取消
            setCancelable(false)
            // 创建要下载的文件对象
            apkFile = File(
                getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                GlobalUtil.appName + "_v" + nameView?.text.toString() + ".apk"
            )
            var disposable = downloadUrl.download()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        // 后台更新
                        closeView?.visibility = View.GONE
                        btnUpdate?.setMaxProgress(it.totalSize.toInt())
                        btnUpdate?.setProgress(it.downloadSize.toInt())
                        btnUpdate?.text = "${it.downloadSizeStr()}/${it.totalSizeStr()}"
                    },
                    onComplete = {
                        // 标记成下载完成
                        state = COMPLETED
                        btnUpdate?.setText(R.string.update_status_successful)
                        apkFile = downloadUrl.file()

                    },
                    onError = {
                        // 标记成下载失败
                        state = FAILED
                        btnUpdate?.setText(R.string.update_status_failed)
                    }
                )
            // 标记成下载中
            state = STARTED
            btnUpdate?.text = "下载中..."
        }

        /**
         * 安装 Apk
         */
        private fun installApk() {
            getContext().startActivity(getInstallIntent())
        }

        /**
         * 获取安装意图
         */
        private fun getInstallIntent(): Intent {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val uri: Uri?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(
                    getContext(),
                    GlobalUtil.appPackage + ".provider",
                    apkFile!!
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            } else {
                uri = Uri.fromFile(apkFile)
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            return intent
        }
    }
}
package com.joekay.network.download

import androidx.annotation.NonNull
import java.io.*
import java.security.DigestInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * @author:  JoeKai
 * @date:  2022/11/25
 * @explain：
 */
open class FileWrapper : File {
    constructor(@NonNull file:File ) : super(file.path)

    /**
     * 打开文件的输入流
     */
    @Throws(FileNotFoundException::class)
    fun openInputStream(): InputStream? {
        return FileInputStream(this)
    }

    /**
     * 打开文件的输出流
     */
    @Throws(FileNotFoundException::class)
    fun openOutputStream(): OutputStream? {
        return FileOutputStream(this)
    }

    companion object{
        /**
         * 创建文件夹
         */
        fun createFolder(targetFolder: File): Boolean {
            if (targetFolder.exists()) {
                if (targetFolder.isDirectory) {
                    return true
                }
                // noinspection ResultOfMethodCallIgnored
                targetFolder.delete()
            }
            return targetFolder.mkdirs()
        }

        /**
         * 获取文件的 md5
         */
        fun getFileMd5(inputStream: InputStream?): String? {
            if (inputStream == null) {
                return ""
            }
            var digestInputStream: DigestInputStream? = null
            try {
                var messageDigest = MessageDigest.getInstance("MD5")
                digestInputStream = DigestInputStream(inputStream, messageDigest)
                val buffer = ByteArray(1024 * 256)
                while (true) {
                    if (digestInputStream.read(buffer) <= 0) {
                        break
                    }
                }
                messageDigest = digestInputStream.messageDigest
                val md5 = messageDigest.digest()
                val sb = StringBuilder()
                for (b in md5) {
                    sb.append(String.format("%02X", b))
                }
                return sb.toString().lowercase(Locale.getDefault())
            } catch (e: NoSuchAlgorithmException) {
                //EasyLog.print(e)
            } catch (e: IOException) {
                //EasyLog.print(e)
            } finally {
                FileUtils.closeStream(inputStream)
                FileUtils.closeStream(digestInputStream)
            }
            return null
        }
    }



}
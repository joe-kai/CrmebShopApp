package com.joekay.network.download

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.text.TextUtils
import okhttp3.MediaType
import java.io.File

/**
 * @author:  JoeKai
 * @date:  2022/11/25
 * @explain：
 */
class FileContentResolver : FileWrapper {
    private var mContentResolver: ContentResolver? = null
    private var mContentUri: Uri? = null

    private var mContentType: MediaType? = null
    private var mFileName: String? = null


    constructor(context: Context, uri: Uri) : super(File(uri.toString())) {
        FileContentResolver(context.contentResolver, uri)
    }

    constructor(resolver: ContentResolver, uri: Uri) : super(File(uri.toString())) {
        FileContentResolver(resolver, uri, "")
    }

    constructor(context: Context, uri: Uri, fileName: String) : super(File(uri.toString())) {
        FileContentResolver(context.contentResolver, uri, fileName)
    }

    constructor(
        resolver: ContentResolver,
        uri: Uri,
        fileName: String
    ) : super(File(uri.toString())) {
        mContentResolver = resolver
        // 请注意这个 uri 是通过 ContentResolver.insert 方法生成的，并且没有经过修改的，否则会导致文件流读取失败
        // 经过测试，ContentResolver.insert 生成的 uri 类型为 Uri.HierarchicalUri 这个内部类的
        mContentUri = uri
        if (!TextUtils.isEmpty(fileName)) {
            mFileName = fileName
            mContentType = ContentType.guessMimeType(fileName)
        } else {
            mFileName = name
            mContentType = ContentType.STREAM
        }
    }
}
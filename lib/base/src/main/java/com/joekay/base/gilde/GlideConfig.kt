package com.joekay.base.gilde

import android.content.Context
import androidx.compose.ui.layout.Placeable
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.joekay.base.R
import java.io.File

/**
 * @author:  JoeKai
 * @date:  2022/11/30
 * @explain：Glide 全局配置
 */
@GlideModule
class GlideConfig : AppGlideModule() {

    companion object {

        /** 本地图片缓存文件最大值 */
        private const val IMAGE_DISK_CACHE_MAX_SIZE: Int = 500 * 1024 * 1024
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // 读写外部缓存目录不需要申请存储权限
        val diskCacheFile = File(context.cacheDir, "glide")
        // 如果这个路径是一个文件
        if (diskCacheFile.exists() && diskCacheFile.isFile) {
            // 执行删除操作
            diskCacheFile.delete()
        }
        // 如果这个路径不存在
        if (!diskCacheFile.exists()) {
            // 创建多级目录
            diskCacheFile.mkdirs()
        }
        builder.setDiskCache {
            DiskLruCacheWrapper.create(diskCacheFile, IMAGE_DISK_CACHE_MAX_SIZE.toLong())
        }
        val calculator: MemorySizeCalculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize: Int = calculator.memoryCacheSize
        val defaultBitmapPoolSize: Int = calculator.bitmapPoolSize
        val customMemoryCacheSize: Long = (1.2 * defaultMemoryCacheSize).toLong()
        val customBitmapPoolSize: Long = (1.2 * defaultBitmapPoolSize).toLong()
        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize))
        builder.setDefaultRequestOptions(
            RequestOptions()
                // 设置默认加载中占位图
                .placeholder(R.drawable.img_loading)
                // 设置默认加载出错占位图
                .error(R.drawable.img_error)

        )

    }

    //override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    //    // Glide 默认使用的是 HttpURLConnection 来做网络请求，这里切换成更高效的 OkHttp
    //    registry.replace(
    //        GlideUrl::class.java,
    //        InputStream::class.java,
    //        OkHttpLoader.Factory(EasyConfig.getInstance().client)
    //    )
    //}

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
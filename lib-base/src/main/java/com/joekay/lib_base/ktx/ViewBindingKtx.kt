package com.joekay.lib_base.ktx

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> Any.getViewBinding(inflater: LayoutInflater, position: Int = 0): VB {
    val vbClass =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
    val inflate = vbClass[position].getDeclaredMethod("inflate", LayoutInflater::class.java)
    return inflate.invoke(null, inflater) as VB
}
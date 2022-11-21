package com.joekay.lib_base.base.action

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import java.util.ArrayList

/**
 * @author:  JoeKai
 * @date:  2022/10/15
 * @content：
 */
interface BundleAction {
    fun initBundle(): Bundle?
    private fun getBundle(): Bundle? {
        return initBundle()
    }

    fun getBundleInt(name: String): Int {
        return getBundleInt(name, 0)
    }

    fun getBundleInt(name: String, defaultValue: Int): Int {
        val bundle: Bundle = getBundle() ?: return defaultValue
        return bundle.getInt(name, defaultValue)
    }

    fun getBundleLong(name: String): Long {
        return getBundleLong(name, 0)
    }

    fun getBundleLong(name: String, defaultValue: Long): Long {
        val bundle: Bundle = getBundle() ?: return defaultValue
        return bundle.getLong(name, defaultValue)
    }

    fun getBundleFloat(name: String): Float {
        return getBundleFloat(name, 0f)
    }

    fun getBundleFloat(name: String, defaultValue: Float): Float {
        val bundle: Bundle = getBundle() ?: return defaultValue
        return bundle.getFloat(name, defaultValue)
    }

    fun getBundleDouble(name: String): Double {
        return getBundleDouble(name, 0.0)
    }

    fun getBundleDouble(name: String, defaultValue: Double): Double {
        val bundle: Bundle = getBundle() ?: return defaultValue
        return bundle.getDouble(name, defaultValue)
    }

    fun getBundleBoolean(name: String): Boolean {
        return getBundleBoolean(name, false)
    }

    fun getBundleBoolean(name: String, defaultValue: Boolean): Boolean {
        val bundle: Bundle = getBundle() ?: return defaultValue
        return bundle.getBoolean(name, defaultValue)
    }

    fun getBundleString(name: String): String {
        return getBundleString(name, "")
    }

    fun getBundleString(name: String, defaultValue: String): String {
        val bundle: Bundle = getBundle() ?: return defaultValue
        return bundle.getString(name, defaultValue)
    }

    fun <P : Parcelable?> getBundleParcelable(name: String): P? {
        val bundle: Bundle = getBundle() ?: return null
        return bundle.getParcelable(name)
    }

    @Suppress("UNCHECKED_CAST")
    fun <S : Serializable?> getBundleSerializable(name: String): S? {
        val bundle: Bundle = getBundle() ?: return null
        return (bundle.getSerializable(name)) as S?
    }

    fun getBundleStringArrayList(name: String): ArrayList<String?>? {
        val bundle: Bundle = getBundle() ?: return null
        return bundle.getStringArrayList(name)
    }

    fun getBundleIntegerArrayList(name: String): ArrayList<Int?>? {
        val bundle: Bundle = getBundle() ?: return null
        return bundle.getIntegerArrayList(name)
    }
}
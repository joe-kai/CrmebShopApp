package com.joekay.base.multiStateView

import android.app.Activity
import android.view.View

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：
 */

fun View.bindMultiState() = MultiStatePage.bindMultiState(this)

fun Activity.bindMultiState() = MultiStatePage.bindMultiState(this)
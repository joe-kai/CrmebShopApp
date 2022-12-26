package com.joekay.module_base.login

import com.joekay.module_base.other.TOKEN_KEY
import com.joekay.module_base.utils.MMKVUtils

/**
 * @author:  JoeKai
 * @date:  2022/12/26
 * @explain：
 */
object LoginManager {

    fun isLogin(): Boolean {
        return MMKVUtils.getString(TOKEN_KEY).isNotEmpty()
    }


}
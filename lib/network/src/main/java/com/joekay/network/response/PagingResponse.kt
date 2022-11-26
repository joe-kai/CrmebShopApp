package com.joekay.network.response

/**
 * @author:  JoeKai
 * @date:  2022/10/1
 * @explain：
 */
data class PagingResponse<T> (
    var page: Int,
    var limit: Int,
    var totalPage: Int,
    var total: Int,
    var list: MutableList<T>
)
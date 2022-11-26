package com.joekay.network.response

/**
 * @Author JoeKay
 * @date [2022/9/30]
 * @function
 */
abstract class BaseResponse<T> {
    abstract val success: Boolean

    abstract var data: T?

    abstract val code: Int?

    abstract val message: String?
}
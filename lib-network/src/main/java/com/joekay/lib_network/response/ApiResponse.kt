package com.joekay.lib_network.response

import com.joekay.lib_network.exception.ErrorException

/**
 * @Author JoeKay
 * @date [2022/9/30]
 * @function
 */
open class ApiResponse<T>(
    override var data: T? = null,
    override val code: Int? = null,
    override val message: String? = null,
) : BaseResponse<T>() {
    constructor(exception: ErrorException) : this(null, exception.code, exception.errorMsg)

    override val success: Boolean
        get() = code == 200
}

class StartResponse<T> : ApiResponse<T>()

data class SuccessResponse<T>(override var data: T? ) : ApiResponse<T>(data)

class EmptyResponse<T> : ApiResponse<T>()

data class FailureResponse<T>(val exception: ErrorException) :
    ApiResponse<T>(exception = exception)



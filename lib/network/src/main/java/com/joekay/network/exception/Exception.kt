package com.joekay.network.exception

import com.google.gson.JsonParseException
import com.joekay.network.response.BaseResponse
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * @Author JoeKay
 * @date [2022/9/30]
 * @function 处理返回的异常
 */
data class ErrorException(
    val code: Int? = 0,
    val errorMsg: String? = null,
) : Exception() {

    constructor(response: BaseResponse<*>) : this(
        response.code, response.message
    )

    constructor(httpError: HttpError) : this(
        httpError.code, httpError.message
    )

    constructor(httpError: HttpError, msg: String?) : this(
        httpError.code, msg
    )
}

enum class HttpError(val code: Int, val message: String){
    NETWORK_ERROR(1000, "网络连接超时，请检查网络"),
    JSON_PARSE_ERROR(1001, "Json 解析失败"),
    NOT_FOUND(1002, "访问链接不存在"),
    UNKNOWN(-100,"未知错误"),
}
fun handleException(throwable: Throwable) = when (throwable) {
    is ConnectException -> ErrorException(HttpError.NETWORK_ERROR)
    is UnknownHostException -> ErrorException(HttpError.NETWORK_ERROR)
    is HttpException -> ErrorException(HttpError.NOT_FOUND, throwable.localizedMessage)
    //{
    //    val errorModel = throwable.response()?.errorBody()?.string()?.run {
    //        Gson().fromJson(this, ErrorModel::class.java)
    //    } ?: ErrorModel()
    //    RequestException(
    //        code = errorModel.code, error = errorModel.error
    //    )
    //}
    is JsonParseException -> ErrorException(HttpError.JSON_PARSE_ERROR, throwable.message)
    is ErrorException -> throwable
    else -> ErrorException(HttpError.UNKNOWN, throwable.message)
}

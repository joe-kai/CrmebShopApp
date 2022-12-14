package com.joekay.network.repository

import com.joekay.network.config.PAGE_SIZE
import androidx.paging.PagingConfig
import com.joekay.network.RetrofitBuilder
import com.joekay.network.config.BASE_URL
import com.joekay.network.exception.ErrorException
import com.joekay.network.exception.handleException
import com.joekay.network.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Author JoeKay
 * @date [2022/9/30]
 * @function
 */
open class BaseRepository<C>(
    serviceClass: Class<C>, baseUrl: String = BASE_URL
) {
    val api: C by lazy {
        RetrofitBuilder.getRetrofit(baseUrl).create(serviceClass)
    }


    companion object {
        var page = 1
        val config = PagingConfig(
            pageSize = PAGE_SIZE,
        )
    }

    protected suspend fun <T> fire(
        block: suspend () -> BaseResponse<T>
    ): BaseResponse<T> = withContext(Dispatchers.IO) {
        var response: BaseResponse<T> = EmptyResponse()
        kotlin.runCatching {
            block.invoke()
        }.onSuccess {
            response = when (it.success) {
                true -> {
                    checkEmptyResponse(it.data)
                }

                false -> FailureResponse(handleException(ErrorException(it)))
            }
        }.onFailure { throwable ->
            response = FailureResponse(handleException(throwable))
        }
        response
    }

    protected suspend fun <T> fireList(
        block: suspend () -> BaseResponse<T>
    ): BaseResponse<T> = withContext(Dispatchers.IO) {
        var response: BaseResponse<T> = EmptyResponse()
        kotlin.runCatching {
            block.invoke()
        }.onSuccess {
            response = when (it.success) {
                true -> {
                    page++
                    checkEmptyResponse(it.data)
                }

                false -> FailureResponse(handleException(ErrorException(it)))
            }
        }.onFailure { throwable ->
            response = FailureResponse(handleException(throwable))
        }
        response
    }

    /**
     * data ??? null????????? data ???????????????????????????????????????????????? onEmpty ??????
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T> checkEmptyResponse(data: T?): BaseResponse<T> =
        if (data is List<*> && data.isEmpty() || data is PagingResponse<*> && data.list.isEmpty()) {
            EmptyResponse()
        } else {
            if (data == null) {
                EmptyResponse()
            } else {
                SuccessResponse(data)
            }
        }


}
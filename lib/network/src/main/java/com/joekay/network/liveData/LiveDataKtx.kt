package com.joekay.network.liveData

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joekay.network.HttpRequestCallback
import com.joekay.network.event.LoadingEvent
import com.joekay.network.event.State
import com.joekay.network.event.StateEvent
import com.joekay.network.exception.ErrorException
import com.joekay.network.observer.IBaseObserver
import com.joekay.network.response.BaseResponse
import com.joekay.network.response.StartResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import kotlin.coroutines.CoroutineContext

/**
 * LoadingDSL
 * 监听 LiveData 的值的变化，回调为 DSL 的形式
 */
inline fun <T> BaseLiveData<T>.observeLoading(
    owner: LifecycleOwner,
    isShowLoading: Boolean = true,
    //isShowToast: Boolean = false,
    crossinline callback: HttpRequestCallback<T>.() -> Unit
) {
    val requestCallback = HttpRequestCallback<T>().apply(callback)
    observe(owner, object : IBaseObserver<T> {
        override fun onStart() {
            if (isShowLoading) {
                EventBus.getDefault().post(LoadingEvent(true))
            }
            requestCallback.startCallback?.invoke()
        }

        override fun onSuccess(data: T) {
            requestCallback.successCallback?.invoke(data)
        }

        override fun onEmpty() {
            requestCallback.emptyCallback?.invoke()
        }

        override fun onFailure(e: ErrorException) {
            //if (isShowToast){
            //    EventBus.getDefault().post(ToastEvent("${e.errorMsg}"))
            //}
            requestCallback.failureCallback?.invoke(e)
        }

        override fun onFinish() {
            if (isShowLoading) {
                EventBus.getDefault().post(LoadingEvent(false))
            }

            requestCallback.finishCallback?.invoke()
        }
    })
}

inline fun <T> BaseLiveData<T>.observeState(
    owner: LifecycleOwner,
    isShowState: Boolean = true,
    //isShowToast:Boolean = false,
    crossinline callback: HttpRequestCallback<T>.() -> Unit
) {
    val requestCallback = HttpRequestCallback<T>().apply(callback)
    observe(owner, object : IBaseObserver<T> {
        override fun onStart() {
            if (isShowState) {
                EventBus.getDefault().post(StateEvent(State.STATE_LOADING))
            }
            requestCallback.startCallback?.invoke()
        }

        override fun onSuccess(data: T) {
            if (isShowState) {
                EventBus.getDefault().post(StateEvent(State.STATE_SUCCESS))
            }
            requestCallback.successCallback?.invoke(data)
        }

        override fun onEmpty() {
            if (isShowState) {
                EventBus.getDefault().post(StateEvent(State.STATE_EMPTY))
            }
            requestCallback.emptyCallback?.invoke()
        }

        override fun onFailure(e: ErrorException) {
            if (isShowState) {
                EventBus.getDefault().post(StateEvent(State.STATE_ERROR))
            }
            //if (isShowToast){
            //    EventBus.getDefault().post(ToastEvent("${e.errorMsg}"))
            //}
            requestCallback.failureCallback?.invoke(e)
        }

        override fun onFinish() {
            requestCallback.finishCallback?.invoke()
        }
    })
}

fun <T> MutableLiveData<BaseResponse<T>>.request(
    viewModel: ViewModel,
    context: CoroutineContext = Dispatchers.Main,
    request: suspend () -> BaseResponse<T>
) {
    viewModel.viewModelScope.launch(context) {
        this@request.postValue(StartResponse())
        this@request.postValue(request())
    }
}
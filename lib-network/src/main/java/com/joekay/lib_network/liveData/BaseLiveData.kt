package com.joekay.lib_network.liveData

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.joekay.lib_network.observer.IBaseObserver
import com.joekay.lib_network.response.BaseResponse

/**
 * @author:  JoeKai
 * @date:  2022/11/21
 * @explain：
 */
class BaseLiveData<T> : MutableLiveData<BaseResponse<T>> {
    /**
     * Creates a MutableLiveData initialized with the given `value`.
     *
     *  @param  value initial value
     */
    constructor(value: BaseResponse<T>?) : super(value)

    /**
     * Creates a MutableLiveData with no value assigned to it.
     */
    constructor() : super()

    /**
     * Adds the given observer to the observers list within the lifespan of the given owner.
     * The events are dispatched on the main thread. If LiveData already has data set, it
     * will be delivered to the observer.
     */
    fun observe(owner: LifecycleOwner, observer: IBaseObserver<T>) {
        super.observe(owner, observer)
    }
}
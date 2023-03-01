package com.neophron.mylibrary.single_use_data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

abstract class SingleUseData<T : Any> {

    protected abstract val liveData: LiveData<Handler<T>>

    abstract fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>)

}
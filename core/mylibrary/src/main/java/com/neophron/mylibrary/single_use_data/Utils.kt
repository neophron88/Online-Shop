@file:Suppress("unused")

package com.neophron.mylibrary.single_use_data

import android.util.Log
import androidx.lifecycle.LifecycleOwner


typealias Observer<T> = (T) -> Unit

fun <T : Any> MutableSingleUseData<T>.toSingleUseData(): SingleUseData<T> = this


fun <T : Any, R> SingleUseData<T>.distinctUntilChanged(
    lifecycleOwner: LifecycleOwner,
    distinctBy: (T) -> R
): SingleUseData<T> {

    var lastData: R? = null

    val singleUseData = MutableSingleUseData<T>()

    this.observe(lifecycleOwner) {
        val newData = distinctBy(it)
        if (lastData != newData) {
            singleUseData.value = it
            lastData = newData
        }
    }
    return singleUseData
}
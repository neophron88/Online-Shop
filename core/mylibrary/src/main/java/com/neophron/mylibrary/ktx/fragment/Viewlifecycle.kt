@file:Suppress("unused")

package com.neophron.mylibrary.ktx.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T> Fragment.viewLifeCycle(block: () -> T) =
    ViewLifeCycle(block)


class ViewLifeCycle<T>(
    val block: () -> T
) : ReadOnlyProperty<Fragment, T> {

    var value: T? = null

    override operator fun getValue(
        thisRef: Fragment,
        property: KProperty<*>
    ): T = value ?: tryToInit(thisRef)

    private fun tryToInit(fragment: Fragment): T {
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (lifecycle.currentState == Lifecycle.State.DESTROYED)
            error("View lifecycle is destroyed")

        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) { value = null }
        })

        val obj = block()
        value = obj
        return obj
    }
}
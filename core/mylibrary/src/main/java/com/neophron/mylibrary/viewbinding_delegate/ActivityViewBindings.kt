package com.neophron.mylibrary.viewbinding_delegate

import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding


/**
 * A delegate that provide your viewbinding within an Activity by lazy.
 **/
inline fun <reified VB : ViewBinding> ComponentActivity.viewBindings(): Lazy<VB> =
    lazy(LazyThreadSafetyMode.NONE) {
        val clazz: Class<VB> = VB::class.java
        val inflate = clazz.getMethod("inflate", LayoutInflater::class.java)
        val bindingObj = inflate.invoke(null, layoutInflater)
        bindingObj as VB
    }

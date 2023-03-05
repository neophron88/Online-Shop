package com.neophron.mylibrary.ktx.fragment

import android.graphics.drawable.GradientDrawable
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner


fun Fragment.getGradientDrawable(@DrawableRes res: Int): GradientDrawable {
    return ContextCompat.getDrawable(requireContext(), res) as GradientDrawable
}

fun Fragment.disableTransitionOverlap() {
    allowEnterTransitionOverlap = false
    allowReturnTransitionOverlap = false
}



inline fun <reified T> Fragment.findParentAs(): T {
    var target: T? = null

    var parent: Fragment? = parentFragment
    while (true) {
        if (parent == null) break
        else if (parent is T) {
            target = parent
            break
        }
        parent = parent.parentFragment

    }

    return target ?: error("No fragment parents, implemented ${T::class}")
}


fun Fragment.getStringOrNull(@StringRes resId: Int?): CharSequence? =
    if (resId == null) null else getString(resId)


typealias HandleOnBackPressed = OnBackPressedCallback.() -> Unit

inline fun Fragment.interceptOnBackPressed(
    lifecycleOwner: LifecycleOwner,
    crossinline callback: HandleOnBackPressed
) {

    requireActivity().onBackPressedDispatcher
        .addCallback(lifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                callback.invoke(this)
            }
        })
}
package com.neophron.mylibrary.ktx

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun Fragment.showToast(@StringRes message: Int) {
    Snackbar
        .make(requireView(), message, Snackbar.LENGTH_LONG)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
        .show()
}
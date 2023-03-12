package com.neophron.mylibrary.ktx

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun View.showToast(@StringRes message: Int) {
    Snackbar
        .make(this, message, Snackbar.LENGTH_LONG)
        .setAnchorView(this)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
        .show()
}
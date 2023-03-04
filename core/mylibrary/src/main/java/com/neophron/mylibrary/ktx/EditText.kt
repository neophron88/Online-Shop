package com.neophron.mylibrary.ktx

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout

fun EditText.behaveLikeTextView() {
    imeOptions = EditorInfo.IME_ACTION_NONE
    isClickable = false
    isCursorVisible = false
    isFocusable = false

    isFocusableInTouchMode = false
    inputType = EditorInfo.TYPE_NULL
}

fun EditText.limitTextAfterDot(limit: Int) {

    this.doAfterTextChanged { editable ->

        val text = editable?.toString() ?: return@doAfterTextChanged

        val dotIndex = text.indexOfFirst { it == '.' }

        val requiredLength = dotIndex + limit + 1
        if (dotIndex != -1 && requiredLength < text.length) {
            this.setText(text.substring(0, requiredLength))
            this.setSelection(this.text.length)
        }
    }

}

fun TextInputLayout.disableErrorWhenTyping() {
    editText?.doAfterTextChanged {
        if (error != null) {
            error = null
            isErrorEnabled = false
        }
    }
}
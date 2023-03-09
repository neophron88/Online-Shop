package com.neophron.mylibrary.ktx

import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout


fun TextInputLayout.disableErrorWhenTyping() {
    editText?.doAfterTextChanged {
        if (error != null) {
            error = null
            isErrorEnabled = false
        }
    }
}
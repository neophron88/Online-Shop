package com.neophron.mylibrary.ktx

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


fun EditText.doAfterTextStopChanging(
    delay: Long = 1000,
    action: (text: Editable?) -> Unit
) {
    this.addTextChangedListener(TextWatcherDelayedImpl(delay, this, action))
}

class TextWatcherDelayedImpl(
    private val delay: Long,
    private val currentEditText: EditText,
    private val block: (s: Editable?) -> Unit
) : TextWatcher {

    private val handler = Handler(Looper.getMainLooper())

    private val runnable = Runnable {
        block(currentEditText.text)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(s: Editable?) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, delay)
    }
}


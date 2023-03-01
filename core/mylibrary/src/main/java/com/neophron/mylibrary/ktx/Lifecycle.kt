package com.neophron.mylibrary.ktx

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

inline fun LifecycleOwner.onDestroy(crossinline block: () -> Unit) {
    this.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) { block() }
    })
}


inline fun LifecycleOwner.onPause(crossinline block: () -> Unit) {
    this.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onPause(owner: LifecycleOwner) { block() }
    })
}

inline fun LifecycleOwner.onStop(crossinline block: () -> Unit) {
    this.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onStop(owner: LifecycleOwner) { block() }
    })
}


fun LifecycleOwner.postDelayed(delayMillis: Long = 0, run: () -> Unit) {
    val handler = Handler(Looper.getMainLooper())

    class Link {
        lateinit var runnable: Runnable
        lateinit var observer: LifecycleObserver
    }

    val link = Link()

    link.runnable = Runnable {
        run.invoke()
        this.lifecycle.removeObserver(link.observer)
    }

    link.observer = object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            handler.removeCallbacks(link.runnable)
        }
    }

    this.lifecycle.addObserver(link.observer)
    handler.postDelayed(link.runnable, delayMillis)
}
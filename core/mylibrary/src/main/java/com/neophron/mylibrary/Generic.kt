package com.neophron.mylibrary


inline fun <T, R> T.map(block: T.() -> R): R = this.block()


inline fun <reified T> Any.takeAs(): T =
    if (this is T) this else throw IllegalStateException(
        "${this::class.java.simpleName} don't implement or inherit ${T::class.java.simpleName}"
    )

fun <T> T?.require(errMsg: String = "The value is required"): T =
    this ?: throw IllegalStateException(errMsg)
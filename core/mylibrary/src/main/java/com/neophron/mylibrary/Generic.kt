package com.neophron.mylibrary


inline fun <T, R> T.map(block: T.() -> R): R = this.block()


inline fun <reified T> Any.takeAs(): T =
    if (this is T) this else throw IllegalStateException(
        "${this::class.java.simpleName} is not subclass ${T::class.java.simpleName}"
    )

fun <T> T?.require(errMsg: String = "The value is required"): T =
    this ?: throw IllegalStateException(errMsg)


inline fun <reified T> T.throwNotHandled(): Nothing =
    throw IllegalStateException("${T::class.java} is not handled")